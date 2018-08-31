package trace.http;

import org.apache.http.Header;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpProcessor;
import trace.TraceParam;
import trace.api.ClientTracer;
import trace.api.FieldType;
import trace.api.Span;
import trace.api.Tracer;

import java.io.IOException;

/**
 * Created by zhaoheng on 18/7/23.
 */
public class TraceHttpProcessor implements HttpProcessor {

    private static final String INFRA_NAME = "HTTP";
    private static final String INFRA_VERSION = "1.1.12";
    private static final String APP_KEY = "";

    @Override
    public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
        String spanName = getSpanname(request);
        //appkey ip port mtrace都可以自动获取
        int remotePort = Helper.DEFAULT_HTTP_PORT;
        TraceParam param = new TraceParam(spanName);
        param.setRemotePort(remotePort);
        //remote appkey为url,以前是这样传的
        param.setRemoteAppKey(getRemoteHost(request));
        param.setInfraName(INFRA_NAME);
        param.setVersion(INFRA_VERSION);
        Span span = Tracer.clientSend(param);
        request.addHeader(FieldType.TraceId.getName(), span.getTraceId());
        request.addHeader(FieldType.SpanId.getName(), span.getSpanId());
        request.addHeader(FieldType.Appkey.getName(), APP_KEY);
        Helper.transferMapToHeadersPutToRequest(span.getForeverContext(), FieldType.TransferContextPre, request);
        Helper.transferMapToHeadersPutToRequest(span.getRemoteOneStepContext(), FieldType.TransferOneContextPre, request);
        if (span.isDebug()) {
            request.setHeader(FieldType.Debug.getName(), "true");
        }
    }

    @Override
    public void process(HttpResponse response, HttpContext context) throws HttpException, IOException {
        try {
            ClientTracer tracer = Tracer.getClientTracer();
            if (tracer.getSpan() != null) {
                Header spannameHeader = response.getFirstHeader(FieldType.SpanName.getName());
                if (spannameHeader != null && !spannameHeader.getValue().isEmpty()) {
                    tracer.getSpan().setSpanName(spannameHeader.getValue());
                }
                Header appkeyHeader = response.getFirstHeader(FieldType.Appkey.getName());
                if (appkeyHeader != null && !appkeyHeader.getValue().isEmpty()) {
                    tracer.getSpan().getRemote().setAppkey(appkeyHeader.getValue());
                }
                Header hostHeader = response.getFirstHeader(FieldType.Host.getName());
                if (hostHeader != null && !hostHeader.getValue().isEmpty()) {
                    tracer.getSpan().getRemote().setHost(hostHeader.getValue());
                }
//                tracer.getSpan().setPackageSize(Helper.getServerResponseHttpPackageSize(response));
                setStatus4HTTP(tracer.getSpan(), response);
                Tracer.clientRecv();
            }
        } catch (Exception e) {
        }

    }


    private void setStatus4HTTP(Span span, HttpResponse response) {
        //分为2XX 3XX 4XX 5XX 方便截取
        String statusCode = String.valueOf(response.getStatusLine().getStatusCode());
        //取得开头字符
        String pre = statusCode.substring(0, 1);
        //jdk 1.7才有objects.equals,不能用!
        if ("2".equals(pre)) {
            span.setStatus(Tracer.STATUS.HTTP_2XX);
        } else if ("3".equals(pre)) {
            span.setStatus(Tracer.STATUS.HTTP_3XX);
        } else if ("4".equals(pre)) {
            span.setStatus(Tracer.STATUS.HTTP_4XX);
        } else if ("5".equals(pre)) {
            span.setStatus(Tracer.STATUS.HTTP_5XX);
        } else {
            span.setStatus(Tracer.STATUS.HTTP_2XX);
        }
    }

    @Deprecated  // Rest风格会导致spanname过多
    private String getSpanname(HttpRequest request) {
        String uri = request.getRequestLine().getUri();
        int index = uri.indexOf('?');
        return index < 0 ? uri : uri.substring(0, index);
    }

    private String getRemoteHost(HttpRequest request) {
        Header[] headers = request.getAllHeaders();
        for (int i = 0; i < headers.length; i++) {
            if ("HOST".equalsIgnoreCase(headers[i].getName())) {
                return "http://" + headers[i].getValue();
            }
        }
        return null;
    }
}
