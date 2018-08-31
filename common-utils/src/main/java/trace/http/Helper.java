package trace.http;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpRequest;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import trace.TraceParam;
import trace.api.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by zhaoheng on 18/7/11.
 */
public class Helper {
    public static final String DEFAULT_URL_REGEX = "^/.*\\.(css|js|ico|gif|dmp|png|jpg|ttf|eot|otf|woff|svg)$";
    public static final String MTRACE_EXURL_REGEX = "mtrace.exurl.regex";
    public static final int FAIL = 10002;
    public static final int DEFAULT_HTTP_PORT = 80;

    private static final Logger LOG = LoggerFactory.getLogger(Helper.class);
    private static final String APP_KEY = "app.key";
    private static final String APP_HOST = "app.host";
    private static final String APP_PORT = "app.port";
    private static final String UNKNOWN = "unknownHost";
    private static Endpoint DEFAULT_LOCAL_ENDPOINT;
    private static String excludeUrlRegex;
    private static Pattern pattern;
    protected static String ipV4 = getLocalIpV4();


    static {
        // init first
        String appkey = string(APP_KEY);
        String ip = getLocalIp();
        DEFAULT_LOCAL_ENDPOINT = new Endpoint(appkey, ip, intOr(APP_PORT, 0));

        if (excludeUrlRegex == null) {
            excludeUrlRegex = System.getProperty(Helper.MTRACE_EXURL_REGEX, Helper.DEFAULT_URL_REGEX);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("excludeUrlRegex = " + excludeUrlRegex);
        }
        try {
            pattern = Pattern.compile(excludeUrlRegex);
        } catch (Exception e) {
            LOG.warn("illegal url regex, " + excludeUrlRegex);
            pattern = Pattern.compile(Helper.DEFAULT_URL_REGEX);
        }
    }


    public static void startTrace(HttpServletRequest request, HttpServletResponse response, String spanname, String appkey, int port) {
        String traceId = Helper.getField(request, FieldType.TraceId.getName());
        String spanId = Helper.getField(request, FieldType.SpanId.getName());
        Boolean debug = Boolean.valueOf(Helper.getField(request, FieldType.Debug.getName()));
        //自动获取 appkey ip port   mtraceConfig获取本地app.properties
        TraceParam param = new TraceParam(spanname);
        //尽量已用户配置为主
        param.setLocalAppKey(appkey);
        param.setLocalPort(port);
        param.setDebug(debug);
        param.setTraceId(traceId);
        param.setSpanId(spanId);
        Tracer.serverRecv(param);
        Span span = Tracer.getServerTracer().getSpan();
        //   span.setPackageSize(Helper.getClientRequestHttpPackageSize(request));
        span.setInfraName(Constants.INFRA_NAME);
        span.setVersion(Constants.INFRA_VERSION);
        String remoteAppkey = Helper.getField(request, FieldType.Appkey.getName());
        Endpoint remote = Helper.getRemote(request);
        if (remoteAppkey == null || remoteAppkey.equals("")) {
            remoteAppkey = remote.getAppkey();
        }
        span.setRemote(remoteAppkey, remote.getHost(), remote.getPort());

        String isTest = getQueryParameter(request.getQueryString(), Constants.IS_TEST);
        if (isTest != null && isTest.equals("true")) {
            Tracer.setTest(true);
        }
        span.getForeverContext().putAll(Helper.transferHeadersToMapByHeaderName(FieldType.TransferContextPre, request));
        span.getRemoteOneStepContext().putAll(Helper.transferHeadersToMapByHeaderName(FieldType.TransferOneContextPre, request));
        //回传给client
        response.addHeader(FieldType.Appkey.getName(), appkey);
        if (spanname != null) {
            response.addHeader(FieldType.SpanName.getName(), spanname);
        }
        response.addHeader(FieldType.Host.getName(), Helper.ipV4);

        final String responseTraceId = span.getTraceId();
        if (responseTraceId != null && !responseTraceId.isEmpty()) {
            response.addHeader(FieldType.TraceId.getName(), responseTraceId);
        }
    }

    public static void afterCompletion(HttpServletRequest request, Exception ex) {
        try {
            final Span span = Tracer.getServerTracer().getSpan();
            if (span != null) {
                //设置HTTP状态
                setStatus4HTTP(span, ex);
                Tracer.serverSend();
            }
        } catch (Exception e) {
            LOG.debug(e.getMessage(), e);
        }
    }

    public static void setStatus4HTTP(final Span span, Exception ex) {
        if (ex == null) {
            span.setStatus(Tracer.STATUS.HTTP_2XX);
        } else {
            //springMVC默认会把所有的Exception最终转换为ServletException 和IOExcption 所以只要用户抛出Exception就会500
            ServerTracer.getInstance().info("http.status.ex", ex.getClass().getName(), System.currentTimeMillis());
            LOG.debug("Exception Http Request, EX : ", ex);
            span.setStatus(Tracer.STATUS.HTTP_5XX);
        }
    }

    /**
     * 转换对应的header为map
     *
     * @param type
     * @param request
     * @return
     */
    public static Map<String, String> transferHeadersToMapByHeaderName(FieldType type, HttpServletRequest request) {
        Enumeration enumeration = request.getHeaderNames();
        Map<String, String> headersMap = new HashMap<String, String>();
        while (enumeration.hasMoreElements()) {
            String HeaderName = (String) enumeration.nextElement();
            if (HeaderName.contains(type.getName())) {
                String value = request.getHeader(HeaderName);
                headersMap.put(HeaderName.substring(HeaderName.lastIndexOf("-") + 1, HeaderName.length()), value);
            }
        }
        return headersMap;
    }

    /**
     * 转换两种context为两种不同的header
     *
     * @param forever
     * @param type
     * @return
     */
    public static void transferMapToHeadersPutToRequest(Map<String, String> forever, FieldType type, HttpRequest req) {
        for (Map.Entry<String, String> entry : forever.entrySet()) {
            if (entry.getKey() == null || entry.getValue() == null) {
                continue;
            }
            String key = format(entry.getKey());
            String value = format(entry.getValue());
            String HeaderPre = "";
            if (type == FieldType.TransferContextPre) {
                HeaderPre = FieldType.TransferContextPre.getName();
            } else {
                HeaderPre = FieldType.TransferOneContextPre.getName();
            }
            //M-Transfer*Context-key
            req.addHeader(new BasicHeader(HeaderPre + key, value));
            // 兼容原来的swimlane逻辑
            if (Tracer.SWIMLANE.equals(key)) {
                req.addHeader(Constants.SWIMLANE, value);
            }
        }
    }

    private static String format(String s) {
        return remove(s, '\0');
    }


    private static String remove(String str, char remove) {
        if (!isEmpty(str) && str.indexOf(remove) != -1) {
            char[] chars = str.toCharArray();
            int pos = 0;

            for (int i = 0; i < chars.length; ++i) {
                if (chars[i] != remove) {
                    chars[pos++] = chars[i];
                }
            }

            return new String(chars, 0, pos);
        } else {
            return str;
        }
    }

    private static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }


    private static String getField(HttpServletRequest request, String key) {
        String value = request.getHeader(key);
        if (isBlank(value)) {
            // TODO 存疑：不直接通过request.getParameter(key)读取参数，可能导致应用读取不到？
            // value = request.getParameter(key);
            value = getQueryParameter(request.getQueryString(), key);
            if (isBlank(value)) {
                value = getCookieValue(request, key);
            }
        }
        return value;
    }


    private static String getQueryParameter(String queryString, String key) {
        if (queryString == null || key == null) {
            return null;
        }
        String[] temp = queryString.split("&");
        for (String pair : temp) {
            if (pair != null && pair.startsWith(key)) {
                String[] paramPair = pair.split("=");
                if ((paramPair.length > 1) && paramPair[0].equals(key)) {
                    return paramPair[1];
                }
            }
        }
        return null;
    }


    public static String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie cookie = getCookie(request, cookieName);
        return cookie == null ? null : cookie.getValue().trim();
    }


    public static Cookie getCookie(HttpServletRequest request, String cookieName) {
        Cookie cookies[] = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie;
                }
            }
        }
        return null;
    }


    public static boolean matchExclude(String action) {
        return pattern.matcher(action).matches();
    }


    private static String string(String key) {
        return System.getProperty(key, "");
    }

    private static int intOr(String key, int defaultValue) {
        String value = string(key);
        try {
            return isBlank(value) ? defaultValue : Integer.valueOf(value);
        } catch (Exception e) {
            return defaultValue;
        }
    }


    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }


    public static String getLocalIpV4() {
        Enumeration<NetworkInterface> networkInterface;
        try {
            networkInterface = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            throw new IllegalStateException(e);
        }
        String ip = "";
        Set<String> ips = new HashSet<String>();
        while (networkInterface.hasMoreElements()) {
            NetworkInterface ni = networkInterface.nextElement();
            // 忽略虚拟网卡的IP,docker 容器的IP
            if (ni.getName().contains("vnic") || ni.getName().contains("docker")
                    || ni.getName().contains("vmnet") || ni.getName().contains("vmbox")) {
                continue;
            }

            Enumeration<InetAddress> inetAddress = ni.getInetAddresses();
            while (inetAddress.hasMoreElements()) {
                InetAddress ia = inetAddress.nextElement();
                if (ia instanceof Inet6Address) {
                    continue; // ignore ipv6
                }
                String thisIp = ia.getHostAddress();
                // 排除 回送地址
                if (!ia.isLoopbackAddress() && !thisIp.contains(":") && !"127.0.0.1".equals(thisIp)) {
                    ips.add(thisIp);
                    if (StringUtils.isBlank(ip)) {
                        ip = thisIp;
                    }
                }
            }
        }

        // 为新办公云主机绑定了两个IP, 只用其 10 段IP
        if (ips.size() >= 2) {
            for (String str : ips) {
                if (str.startsWith("10.")) {
                    ip = str;
                    break;
                }
            }
        }

        if (StringUtils.isBlank(ip)) {
            //throw new RuntimeException("can not find local ip!");
            LOG.warn("can't getLocalIp");
        }

        return ip;
    }

    public static String getLocalIp() {
        Enumeration<NetworkInterface> networkInterface;
        try {
            networkInterface = NetworkInterface.getNetworkInterfaces();
            while (networkInterface.hasMoreElements()) {
                NetworkInterface ni = networkInterface.nextElement();
                Enumeration<InetAddress> inetAddress = ni.getInetAddresses();
                while (inetAddress.hasMoreElements()) {
                    InetAddress ia = inetAddress.nextElement();
                    if (ia instanceof Inet6Address)
                        continue; // ignore ipv6
                    String thisIp = ia.getHostAddress();
                    if (thisIp != null && !ia.isLoopbackAddress() && !thisIp.contains(":") && !thisIp.startsWith("127.0.")) {
                        return thisIp;
                    }
                }
            }
        } catch (SocketException e) {
            LOG.warn("can't getLocalIp", e);
        }
        return "127.0.0.1";
    }

    public static Endpoint getRemote(HttpServletRequest httpServletRequest) {
        String auth = httpServletRequest.getHeader("Authorization");
        String clientId = "";
        if (auth != null) {
            int start = auth.indexOf(' ') + 1;
            int end = auth.indexOf(':');
            if (start >= 0 && end >= 0) {
                clientId = auth.substring(start, end);
            }
        }
        String remoteIp = getRealIp(httpServletRequest);
        int remotePort = httpServletRequest.getRemotePort();
        return new Endpoint(clientId, remoteIp, remotePort);
    }

    private static String getRealIp(HttpServletRequest request) {
        String ip = head(request, "X-Real-IP");
        if (ip != null && !UNKNOWN.equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = head(request, "X-Forwarded-For");
        if (ip != null) {
            int index = ip.indexOf(',');
            // 多次反向代理后会有多个IP值，第一个为真实IP。
            return (index >= 0) ? ip.substring(0, index) : ip;
        }
        ip = request.getHeader("Proxy-Client-IP");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip == null ? "unkown" : ip;
    }

    private static String head(HttpServletRequest req, String s) {
        return req.getHeader(s);
    }

}
