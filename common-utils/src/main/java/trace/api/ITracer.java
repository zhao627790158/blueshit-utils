package trace.api;

import trace.TraceParam;

import java.util.Map;

public interface ITracer {

    Span getSpan();

    Span record();

    Span record(String name);

    /**
     * Trace 记录开始
     *
     * @param param 服务参数信息
     */
    Span record(TraceParam param);

    Span recordAsync();

    Span recordAsync(String name);

    Span recordAsync(TraceParam param);

    /**
     * Trace 数据归档
     */
    Span flush();

    Span flushAsync(final Span span);

    void clearCurrentSpan();

    void setTraceId(String traceId);

    /**
     * 获取TraceId
     *
     * @return
     */
    String getTraceId();

    void setSpanId(String spanId);

    String getSpanId();

    void setLocalIp(String ip);

    String getLocalIp();

    void setLocalPort(int port);

    int getLocalPort();

    void setRemoteIp(String ip);

    String getRemoteIp();

    void setRemotePort(int port);

    int getRemotePort();

    void setLocalAppKey(String appKey);

    String getLocalAppKey();

    void setRemoteAppKey(String appKey);

    String getRemoteAppKey();


    void addAnnotation(String key, String value);

    /**
     * 自定义埋点数据, 建立ES索引
     *
     * @param key
     * @param value
     * @param timestamp
     */
    void addAnnotation(String key, String value, long timestamp);

    void info(String key, String value);

    /**
     * 自定义埋点数据, 不会建立ES索引
     *
     * @param key
     * @param value
     * @param timestamp
     */
    void info(String key, String value, long timestamp);

    void setStatus(Tracer.STATUS status);

    void setName(String name);

    String getName();

    void setInfraName(String infraName);

    void setInfraVersion(String version);

    void setSize(int size);

    void setAsync(boolean async);

    void setSample(boolean sample);

    boolean isSample();

    void putAllContext(Map<String, String> map);

    String putContext(String key, String value);

    String getContext(String key);

    void putAllLocalContext(Map<String, String> map);

    String putLocalContext(String key, String value);

    String putLocalOneStepContext(String key, String value);

    String getLocalOneStepContext(String key);

    void putAllRemoteContext(Map<String, String> map);

    String putRemoteContext(String key, String value);

    String putRemoteOneStepContext(String key, String value);

    String getRemoteOneStepContext(String key);


    Map<String, String> getAllContext();

    Map<String, String> getAllLocalContext();

    Map<String, String> getAllLocalOneStepContext();

    Map<String, String> getAllRemoteContext();

    Map<String, String> getAllRemoteOneStepContext();

    void destroyContext();


    void setForce(boolean force);

    boolean isForce();
}