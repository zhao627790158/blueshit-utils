package cn.zh.blueshit.common;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by zhaoheng on 2016/6/17.
 */
public class RequestUtils {


    private static final Log log = LogFactory.getLog(RequestUtils.class);


    public RequestUtils() {
    }

    public static String getCurrentUrl(HttpServletRequest request, String URIEncoding) {
        //http://localhost/test.do?a=b&c=d&e=f 通过request.getQueryString()得到的是a=b&c=d&e=f
        String queryString = request.getQueryString();
        List parameterNamesByGet = parameterNamesByGet(queryString);
        int paramSizeByGet = parameterNamesByGet.size();
        //将包含你单里面所有input标签的数据，以其name为key，以其value为值
        int paramTotal = request.getParameterMap().size();
        if (paramTotal == 0 && paramSizeByGet == 0) {
            return request.getRequestURL().toString();
        } else if ((paramTotal <= 0 || paramTotal != paramSizeByGet) && (paramTotal != 0 || paramSizeByGet <= 0)) {
            StringBuffer url = request.getRequestURL();
            url.append("?");
            if (paramSizeByGet > 0) {
                url.append(queryString);
            }
            String afterConvert = covertToGet(parameterNamesByGet, request.getParameterMap(), URIEncoding);
            if (afterConvert.trim().length() > 0) {
                url.append("&").append(afterConvert);
            }

            return url.toString();
        } else {
            return request.getRequestURL() + "?" + queryString;
        }

    }

    public static String getCurrentURL(HttpServletRequest request) {
        String queryString = request.getQueryString();
        String requestURL = request.getRequestURL().toString();
        if (StringUtils.isBlank(queryString)) {
            return requestURL;
        } else {
            String currentURL = requestURL + "?" + queryString;
            return currentURL;
        }
    }

    /**
     * 如果有form提交的参数,将参数添加到url后面
     *
     * @param parameterNamesByGet
     * @param parameterMap
     * @param URIEncoding
     * @return
     */
    private static String covertToGet(Collection<String> parameterNamesByGet, Map<String, String[]> parameterMap, String URIEncoding) {
        if (parameterMap.size() == 0) {
            return "";
        } else {
            StringBuffer postParams = new StringBuffer();
            Set parameterNameSet = parameterMap.keySet();
            Iterator it = parameterNameSet.iterator();
            while (it.hasNext()) {
                String key = (String) it.next();
                if (!parameterNamesByGet.contains(key)) {
                    postParams.append(key).append("=").append(encode(((String[]) parameterMap.get(key))[0], URIEncoding)).append("&");
                }
            }
            return postParams.toString().substring(0, postParams.length() - 1);
        }
    }

    /**
     * 获取请求参数name集合
     *
     * @param queryString
     * @return
     */
    private static List<String> parameterNamesByGet(String queryString) {
        //a=b&c=d&e=f
        ArrayList parameterNames = new ArrayList();
        if (StringUtils.isNotEmpty(queryString)) {
            String[] params = queryString.split("&");

            for (int i = 0; i < params.length; ++i) {
                if (params[i].contains("=")) {
                    parameterNames.add(params[i].split("=")[0]);
                }
            }
        }

        return parameterNames;
    }

    public static String encode(String value, String charset) {
        if (value != null && value.length() != 0) {
            try {
                return URLEncoder.encode(value, charset);
            } catch (UnsupportedEncodingException var3) {
                log.error("encoding charset unsupported, " + charset);
                return value;
            }
        } else {
            return "";
        }
    }

    public static String decode(String value, String charset) {
        if (value != null && value.length() != 0) {
            try {
                return URLDecoder.decode(value, charset);
            } catch (UnsupportedEncodingException var3) {
                log.error("decoding charset unsupported, " + charset);
                return value;
            }
        } else {
            return "";
        }
    }

    /**
     * 清除cookie
     *
     * @param request
     * @param response
     * @param cookieName
     * @param path
     * @param domain
     */
    public static void clearCookie(HttpServletRequest request, HttpServletResponse response, String cookieName, String path, String domain) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            Cookie[] cookies1 = cookies;
            int len = cookies.length;

            for (int i = 0; i < len; ++i) {
                Cookie cookie = cookies1[i];
                if (cookie.getName().equals(cookieName)) {
                    cookie.setMaxAge(0);
                    cookie.setValue((String) null);
                    cookie.setPath(path);
                    cookie.setDomain(domain);
                    response.addCookie(cookie);
                    break;
                }
            }
        }

    }

    public static Cookie setCookie(HttpServletRequest request, HttpServletResponse response, String name, String value, int maxAge, String path, String domain) {
        log.debug("CookieUtils.setCookie " + name + ":" + value);
        response.setHeader("P3P", "CP=\"CURa ADMa DEVa PSAo PSDo OUR BUS UNI PUR INT DEM STA PRE COM NAV OTC NOI DSP COR\"");
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        if (path != null) {
            cookie.setPath(path);
        }

        cookie.setDomain(domain);
        response.addCookie(cookie);
        return cookie;
    }

    public static String getRootDomain(HttpServletRequest request) {
        String hostStr = request.getServerName();
        String realDomain = null;
        String[] arrayDomain = null;
        if (null != hostStr) {
            hostStr = hostStr.toLowerCase();
            arrayDomain = hostStr.split("\\.");
            //[www,jd,com]
            int arrayNum = arrayDomain.length - 1;
            if (arrayNum > 0) {
                //com
                String end = arrayDomain[arrayNum];
                //jd
                String main = arrayDomain[arrayNum - 1];
                if (end.equals("cn")) {
                    if (!"com".equals(main) && !"net".equals(main) && !"edu".equals(main) && !"org".equals(main) && !"gov".equals(main)) {
                        realDomain = main + "." + end;
                    } else {
                        realDomain = arrayDomain[arrayNum - 2] + "." + main + "." + end;
                    }
                } else {
                    realDomain = main + "." + end;
                }
            }
        }
        return realDomain;
    }

    public static String getRemoteIp(HttpServletRequest request) {
        if (request == null) {
            return null;
        } else {
            String ip = request.getHeader("x-forwarded-for");
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }

            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }

            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }

            if (StringUtils.isNotEmpty(ip) && ip.contains(",")) {
                ip = ip.substring(0, ip.indexOf(","));
            }

            return ip != null ? ip.split(":")[0] : null;
        }
    }

    public static boolean isSSL(HttpServletRequest request) {
        String protocol = request.getHeader("X-Proto");
        return StringUtils.isNotBlank(protocol) && protocol.trim().equalsIgnoreCase("SSL");
    }


    public static void main(String[] args) {
        String hostStr = "http://www.qq.com";
        String[] split = hostStr.split("\\.");

    }


}
