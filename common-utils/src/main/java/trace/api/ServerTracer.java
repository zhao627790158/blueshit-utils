package trace.api;

import trace.TraceParam;

import java.util.Map;

/**
 * Created by zhaoheng on 18/7/11.
 */
public class ServerTracer extends AbstractTracer {


    private static final ServerTracer instance = new ServerTracer();


    @Override
    public Span getSpan() {
        return TraceContext.getCurrentServerSpan();
    }

    @Override
    public Span record(TraceParam param) {
        Span span = recordAsync(param);
        TraceContext.setCurrentServerSpan(span);
        return span;
    }

    @Override
    public Span recordAsync(TraceParam param) {
        String traceId = param.getTraceId();
        String spanId = param.getSpanId();
        // 如果传递了traceId并且是有效的则按照传递的traceId 生成Span,否则重新生成Span
        Span span;
        if (isValidTraceId(traceId)) {
            span = new Span(traceId, spanId, param.getSpanName());
        } else {
            span = new Span(param.getSpanName());
        }
        if (param.isDebug()) {
            span.setDebug(true);
        } else {
            span.setDebug(ConfigSingleton.getConfig().isSample(span));
        }
        span.setLocal(param.getLocalAppKey(), param.getLocalIp(), param.getLocalPort());
        span.setRemote(param.getRemoteAppKey(), param.getRemoteIp(), param.getRemotePort());
        span.setStart(System.currentTimeMillis());
        span.setType(Span.SIDE.SERVER);
        span.setInfraName(param.getInfraName());
        span.setPackageSize(param.getPackageSize());
        span.setVersion(param.getVersion());
        span.setExtend(param.getExtend());
        Map foreverContextMap = span.getForeverContext();
        if (param.getForeverContext() != null && !param.getForeverContext().isEmpty()) {
            foreverContextMap.putAll(param.getForeverContext());
        }
        if (param.getOneStepContext() != null && !param.getOneStepContext().isEmpty()) {
            span.getRemoteOneStepContext().putAll(param.getOneStepContext());
        }
        return span;
    }

    @Override
    public void clearCurrentSpan() {
        TraceContext.setCurrentServerSpan(null);
    }

    @Override
    Span genVirtualContext() {
        Span span = SpanFactory.getVirtualSpan();
        TraceContext.setCurrentServerSpan(span);
        return span;
    }


    public static ServerTracer getInstance() {
        return instance;
    }


}
