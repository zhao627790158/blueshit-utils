package trace.api;

import trace.TraceParam;

import java.util.Map;

/**
 * Created by zhaoheng on 18/7/11.
 */
public abstract class AbstractTracer implements ITracer {
    @Override
    public Span record() {
        return record(new TraceParam());
    }

    @Override
    public Span record(String name) {
        return record(new TraceParam(name));
    }

    @Override
    public Span recordAsync() {
        return recordAsync(new TraceParam());
    }

    @Override
    public Span recordAsync(String name) {
        return recordAsync(new TraceParam(name));
    }

    @Override
    public void setTraceId(String traceId) {
        Span span = getSpan();
        if (span != null) {
            span.setTraceId(traceId);
        }
    }

    @Override
    public String getTraceId() {
        Span span = getSpan();
        return span != null ? span.getTraceId() : null;
    }

    @Override
    public void setSpanId(String spanId) {
        Span span = getSpan();
        if (span != null) {
            span.setSpanId(spanId);
        }
    }

    @Override
    public String getSpanId() {
        Span span = getSpan();
        return span != null ? span.getSpanId() : null;
    }

    @Override
    public void addAnnotation(String key, String value) {
        addAnnotation(key, value, System.currentTimeMillis());
    }

    @Override
    public void addAnnotation(String key, String value, long timestamp) {
        if (key != null && value != null) {
            KVAnnotation kvAnnotation = new KVAnnotation(key, value);
            kvAnnotation.setTs(timestamp);
            Span span = getSpan();
            if (span != null)
                span.addKvAnnotation(kvAnnotation);
        }
    }

    @Override
    public void info(String key, String value) {
        info(key, value, System.currentTimeMillis());
    }

    @Override
    public void info(String key, String value, long timestamp) {
        if (key != null && value != null) {
            KVAnnotation kvAnnotation = new KVAnnotation(key, value);
            kvAnnotation.setTs(timestamp);
            kvAnnotation.setIndex(false);
            Span span = getSpan();
            if (span != null)
                span.addKvAnnotation(kvAnnotation);

        }
    }

    protected boolean isValidTraceId(String traceId) {
        return traceId != null && !traceId.isEmpty() && !Validate.isUuidStr(traceId);
    }

    protected void collect(final Span span) {
        if (TraceManager.isCollect() && span.getSpanName() != null) {
            CollectorSingleton.getInstance().collect(span);
        }
    }

    @Override
    public Span flush() {
        Span span = getSpan();
        if (span != null) {
            span.setEnd(System.currentTimeMillis());
            collect(span);
            clearCurrentSpan();
        }
        return span;
    }

    @Override
    public Span flushAsync(final Span span) {
        if (span != null) {
            span.setEnd(System.currentTimeMillis());
            collect(span);
        }
        return span;
    }

    @Override
    public void setStatus(Tracer.STATUS status) {
        Span span = getSpan();
        if (span != null) {
            span.setStatus(status);
        }
    }

    @Override
    public void setName(String name) {
        Span span = getSpan();
        if (span != null) {
            span.setSpanName(name);
        }

    }

    @Override
    public String getName() {
        Span span = getSpan();
        if (span != null) {
            return span.getSpanName();
        }
        return null;
    }

    @Override
    public void setLocalIp(String ip) {
        Span span = getSpan();
        if (span != null) {
            span.setLocalIp(ip);
        }
    }

    @Override
    public String getLocalIp() {
        Span span = getSpan();
        return span == null ? null : span.getLocalIp();
    }


    @Override
    public void setLocalPort(int port) {
        Span span = getSpan();
        if (span != null) {
            span.setLocalPort(port);
        }
    }

    @Override
    public int getLocalPort() {
        Span span = getSpan();
        return span == null ? 0 : span.getLocalPort();
    }

    @Override
    public void setInfraName(String infraName) {
        Span span = getSpan();
        if (span != null) {
            span.setInfraName(infraName);
        }
    }

    @Override
    public void setInfraVersion(String version) {
        Span span = getSpan();
        if (span != null) {
            span.setVersion(version);
        }
    }

    @Override
    public void setSize(int size) {
        Span span = getSpan();
        if (span != null) {
            span.setPackageSize(size);
        }

    }


    @Override
    public void setAsync(boolean async) {
        Span span = getSpan();
        if (span != null) {
            span.setAsync(async);
        }

    }

    @Override
    public void setSample(boolean sample) {
        Span span = getSpan();
        if (span != null) {
            span.setDebug(sample);
        }

    }

    @Override
    public boolean isSample() {
        Span span = getSpan();
        return span != null && span.isDebug();

    }

    @Override
    public void setRemoteAppKey(String appKey) {
        Span span = getSpan();
        if (span != null) {
            span.setRemoteAppKey(appKey);
        }
    }

    @Override
    public void setRemoteIp(String ip) {
        Span span = getSpan();
        if (span != null) {
            span.setRemoteIp(ip);
        }
    }

    @Override
    public String getRemoteAppKey() {
        Span span = getSpan();
        return span != null ? span.getRemoteAppKey() : null;

    }

    @Override
    public String getRemoteIp() {
        Span span = getSpan();
        return span != null ? span.getRemoteIp() : null;
    }

    @Override
    public void setRemotePort(int port) {
        Span span = getSpan();
        if (span != null) {
            span.setRemotePort(port);
        }
    }

    @Override
    public int getRemotePort() {
        Span span = getSpan();
        return span != null ? span.getRemotePort() : 0;
    }

    @Override
    public void setLocalAppKey(String appKey) {
        Span span = getSpan();
        if (span != null) {
            span.setLocalAppKey(appKey);
        }
    }

    @Override
    public String getLocalAppKey() {
        Span span = getSpan();
        return span != null ? span.getLocalAppKey() : null;
    }

    @Override
    public String putContext(String key, String value) {
        Span span = getSpan();
        if (span == null) {
            span = genVirtualContext();
        }
        return span.getForeverContext().put(key, value);
    }

    @Override
    public void putAllContext(Map<String, String> map) {
        Span span = getSpan();
        if (span == null) {
            span = genVirtualContext();
        }
        span.getForeverContext().putAll(map);
    }


    @Override
    public String getContext(String key) {
        Span span = getSpan();
        if (span != null) {
            return span.getForeverContext().get(key);
        } else {
            // 上下文span 不存在的请求，获取本地的泳道和cell信息
            if (Tracer.SWIMLANE.equals(key)) {
                return ConfigSingleton.getConfig().getLocalSwimlane();
            }
        }
        return null;
    }


    @Override
    public String putLocalContext(String key, String value) {
        return putLocalOneStepContext(key, value);
    }

    @Override
    public String putLocalOneStepContext(String key, String value) {
        Span span = getSpan();
        if (span != null) {
            return span.getLocalOneStepContext().put(key, value);
        }
        return null;

    }

    @Override
    public void putAllLocalContext(Map<String, String> map) {
        Span span = getSpan();
        if (span != null) {
            span.getLocalOneStepContext().putAll(map);
        }
    }


    @Override
    public String getLocalOneStepContext(String key) {
        Span span = getSpan();
        if (span != null) {
            return span.getLocalOneStepContext().get(key);
        }
        return null;

    }

    @Override
    public String putRemoteContext(String key, String value) {
        return putRemoteOneStepContext(key, value);
    }

    @Override
    public String putRemoteOneStepContext(String key, String value) {
        Span span = getSpan();
        if (span != null) {
            return span.getRemoteOneStepContext().put(key, value);
        }
        return null;

    }

    @Override
    public void putAllRemoteContext(Map<String, String> map) {
        Span span = getSpan();
        if (span != null) {
            span.getRemoteOneStepContext().putAll(map);
        }
    }


    @Override
    public String getRemoteOneStepContext(String key) {
        Span span = getSpan();
        if (span != null) {
            return span.getRemoteOneStepContext().get(key);
        }
        return null;

    }

    @Override
    public Map<String, String> getAllContext() {
        Span span = getSpan();
        if (span != null) {
            return span.getForeverContext();
        }
        return null;
    }

    @Override
    public Map<String, String> getAllLocalContext() {
        return getAllLocalOneStepContext();
    }

    @Override
    public Map<String, String> getAllLocalOneStepContext() {
        Span span = getSpan();
        if (span != null) {
            return span.getLocalOneStepContext();
        }
        return null;

    }

    @Override
    public Map<String, String> getAllRemoteContext() {
        return getAllRemoteOneStepContext();
    }

    @Override
    public Map<String, String> getAllRemoteOneStepContext() {
        Span span = getSpan();
        if (span != null) {
            return span.getRemoteOneStepContext();
        }
        return null;

    }

    @Override
    public void destroyContext() {
        Span span = getSpan();
        if (span != null && span.isVirtual()) {
            clearCurrentSpan();
        }
    }

    @Override
    public void setForce(boolean force) {
        Span span = getSpan();
        if (span != null) {
            span.setForce(force);
        }

    }

    @Override
    public boolean isForce() {
        Span span = getSpan();
        return (span != null) && span.isForce();
    }

    abstract Span genVirtualContext();


}
