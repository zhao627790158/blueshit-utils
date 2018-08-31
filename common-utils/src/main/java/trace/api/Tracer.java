package trace.api;

import trace.TraceParam;

/**
 * Created by zhaoheng on 18/7/11.
 */
public class Tracer {

    public static final String CUSTOM_INFRA = "custom";
    public static final String IS_TEST = "PT_IS_TEST";
    public static final String SWIMLANE = "INF_SWIMLANE";
    public static final String CELL = "INF_CELL";
    private static final String GRAY_RELEASE_PREFIX = "gray-release-";


    public static Span serverRecv(TraceParam traceParam) {
        ServerTracer serverTracer = ServerTracer.getInstance();
        return serverTracer.record(traceParam);
    }


    public static ServerTracer getServerTracer() {
        return ServerTracer.getInstance();
    }


    /**
     * Server Send : 异步情况下作为服务端在返回调用结果前埋点,表示该次调用的server端追踪结束
     * 同时收集归档上下文{@link Span}
     *
     * @return {@link Span} 上下文信息
     */
    public static Span serverSend() {
        return ServerTracer.getInstance().flush();
    }

    /**
     * 埋点服务线程中调用, 否则无作用
     * 获取业务端自定义参数
     *
     * @see #putContext(String, String)
     */
    public static String getContext(String key) {
        return getServerTracer().getContext(key);
    }


    /**
     * 埋点服务线程中调用, 否则无作用
     * 跨中间件传递业务端自定义参数,该kv对会在RPC请求中一直传递下去
     * EG : A服务中putContext("uid","123456"), 然后服务A->B->C,
     * 在B,C服务中调用getContext("uid"){@link #getContext(String)},都会得到"123456"
     */
    public static String putContext(String key, String value) {
        return getServerTracer().putContext(key, value);
    }

    /**
     * 埋点服务线程中调用, 否则无作用
     * 只有第一次传入标识时有效
     *
     * @param flag 是否是压测标识
     */
    public static void setTest(boolean flag) {
        Tracer.putContext(IS_TEST, String.valueOf(flag));
    }

    /**
     * Client Send : 同步调用情况下作为调用方在发起调用前埋点(需要与server处理线程在同线程中,否则不能连接调用链)
     * 生成调用上下文 {@link Span},在{@link ThreadLocal} 中传递
     *
     * @param traceParam 追踪参数, {@link TraceParam#TraceParam(String)} spanname(方法名)必填
     * @return {@link Span} 调用上下文
     * @see ClientTracer#record(TraceParam)
     */
    public static Span clientSend(TraceParam traceParam) {
        ClientTracer clientTracer = ClientTracer.getInstance();
        return clientTracer.record(traceParam);
    }

    public static Span clientSend() {
        return ClientTracer.getInstance().record();
    }

    /**
     * Client Send Async : 异步调用情况下作为调用方在发起调用前埋点(需要与server处理线程在同线程中,否则不能连接调用链)
     * 生成调用上下文{@link Span},异步情况下需要自己手动传递返回的上下文信息
     *
     * @param traceParam 追踪参数, {@link TraceParam#TraceParam(String)} spanname(方法名)必填
     * @return {@link Span} 调用上下文
     * @see ClientTracer#recordAsync(TraceParam)
     */
    public static Span clientSendAsync(TraceParam traceParam) {
        ClientTracer clientTracer = ClientTracer.getInstance();
        return clientTracer.recordAsync(traceParam);
    }

    public static Span clientSendAsync() {
        return ClientTracer.getInstance().recordAsync();
    }

    public static Span clientSendAsync(String name) {
        return ClientTracer.getInstance().recordAsync(name);
    }

    public static Span clientRecv() {
        return ClientTracer.getInstance().flush();
    }

    /**
     * 获取Client Tracer 对象, 主要用于Client端埋点使用
     *
     * @return ClientTracer
     */
    public static ClientTracer getClientTracer() {
        return ClientTracer.getInstance();
    }


    public enum STATUS {
        SUCCESS(0), EXCEPTION(1), TIMEOUT(2), DROP(3), HTTP_2XX(12), HTTP_3XX(13), HTTP_4XX(14), HTTP_5XX(15);

        private int value;

        STATUS(int i) {
            this.value = i;
        }

        public int getValue() {
            return value;
        }
    }


}
