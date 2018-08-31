package trace.api;

/**
 * Created by zhaoheng on 18/7/11.
 */
public class SpanFactory {


    public static Span getVirtualSpan() {
        Span span = new Span();
        span.setVirtual(true);
        return span;
    }

    /**
     * 通过server端的调用上下文生成client端的调用上下文
     * 分成三种情况:
     * 1. 如果无父节点, 那么生成一个Span结构并返回
     * 2. 如果有父节点，但是父节点是虚节点，那么生成一个新的Span，并继承父节点的自定义context信息
     * 3. 如果有父节点，且不为虚节点，那么不仅继承自定义context信息，还继承父节点的traceId， spanId，ip，port, debug等信息
     * 继承server端调用上下文的traceId, local appkey, local ip, local port
     *
     * @param name 方法名
     * @return client端的调用上下文
     */
    public static Span genClientSpan(Span parentSpan, String name) {
        if (parentSpan == null) {
            return new Span(name);
        }
        Span currentSpan;
        if (parentSpan.isVirtual()) {
            currentSpan = new Span(name);
        } else {
            final String clientSpanId = parentSpan.getSpanId() + "." + parentSpan.nextSpanNum();
            currentSpan = new Span(parentSpan.getTraceId(), clientSpanId, name);
            currentSpan.setLocal(parentSpan.getLocalAppKey(), parentSpan.getLocalIp(), parentSpan.getLocalPort());
            currentSpan.setParent(parentSpan);
            if (parentSpan.isDebug()) {
                currentSpan.setDebug(true);
            }
        }
        if (!parentSpan.getForeverContext().isEmpty()) {
            synchronized (parentSpan.getForeverContext()) {
                currentSpan.getForeverContext().putAll(parentSpan.getForeverContext());
            }
        }
        if (!parentSpan.getLocalOneStepContext().isEmpty()) {
            synchronized (parentSpan.getLocalOneStepContext()) {
                currentSpan.getRemoteOneStepContext().putAll(parentSpan.getLocalOneStepContext());
            }
        }

        return currentSpan;
    }

}
