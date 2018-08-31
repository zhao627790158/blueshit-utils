package trace.api;

import trace.TraceParam;

/**
 * Created by zhaoheng on 18/7/23.
 */
public class ClientTracer extends AbstractTracer {

    private static final ClientTracer instance = new ClientTracer();

    private ClientTracer() {
    }

    public static ClientTracer getInstance() {
        return instance;
    }


    @Override
    public Span getSpan() {
        return TraceContext.getCurrentClientSpan();
    }

    @Override
    public Span record(TraceParam param) {
        Span span = recordAsync(param);
        TraceContext.setCurrentClientSpan(span);
        return span;
    }

    @Override
    public Span recordAsync(TraceParam param) {
        //获取当前serverspan,用来作为生成clientspan的parent
        final Span currentServerSpan = TraceContext.getCurrentServerSpan();
        final Span span = SpanFactory.genClientSpan(currentServerSpan, param.getSpanName());
        span.setStart(System.currentTimeMillis());
        if (param.isDebug()) {
            span.setDebug(true);
        }
        if (param.getForeverContext() != null && !param.getForeverContext().isEmpty()) {
            span.getForeverContext().putAll(param.getForeverContext());
        }
        if (param.getOneStepContext() != null && !param.getOneStepContext().isEmpty()) {
            span.getRemoteOneStepContext().putAll(param.getOneStepContext());
        }

        span.setLocal(param.getLocalAppKey(), param.getLocalIp(), param.getLocalPort());
        span.setRemote(param.getRemoteAppKey(), param.getRemoteIp(), param.getRemotePort());
        span.setType(Span.SIDE.CLIENT);
        span.setInfraName(param.getInfraName());
        span.setVersion(param.getVersion());
        span.setPackageSize(param.getPackageSize());
        span.setExtend(param.getExtend());
        return span;
    }

    @Override
    public void clearCurrentSpan() {
        TraceContext.setCurrentClientSpan(null);
    }

    @Override
    Span genVirtualContext() {
        Span span = SpanFactory.getVirtualSpan();
        TraceContext.setCurrentClientSpan(span);
        return span;
    }
}
