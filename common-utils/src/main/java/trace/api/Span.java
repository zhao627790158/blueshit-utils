package trace.api;


import lombok.Data;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zhaoheng on 18/7/11.
 */
@Data
public class Span {

    private static final int ANNOTATION_MAX_SIZE = 100;

    private String traceId;                                   // 一次请求的全局唯一id
    private String spanId;                                    // 调用关系id, 标识一次trace中的某一次rpc调用, 签名方式命名, EG : 0, 0.1, 0.2, 01.1
    private AtomicInteger currentSpanNum = new AtomicInteger(0);    // 标识分配到了第几个span, 用于生成调用下游的spanId
    private String spanName;                                   // 调用接口的Class Name + "." + Method Name
    private final Endpoint local = new Endpoint("", "", 0);         // 本地的appKey, ip, port 信息
    private final Endpoint remote = new Endpoint("", "", 0);        // 远端的appKey, ip, port 信息
    private long start;                                             // 开始的时间
    private long end;                                               // 结束的时间
    private SIDE type = SIDE.SERVER;                                // 是client span 还是 server span, 0 client, 1 server
    private Tracer.STATUS status = Tracer.STATUS.SUCCESS;           // 服务返回状态
    private boolean debug = false;                                  // 调用链数据采样字段, 1 采样, 0 不采样. 虽然是short类型，但是上层使用都是用成bool
    private boolean sample = false;                                 // 性能数据采样字段 null未标识，true采样，false 不采样, 1.0.6 版本之前使用，后续版本废弃
    private boolean async = false;                                  // 异步标识
    private boolean force = false;
    private String infraName;                                       // 中间件名, 用于判断中间件类别
    private String version;                                         // 中间件版本
    private int packageSize;                                        // 传输的包大小
    private volatile List<KVAnnotation> kvAnnotations = null;                // 自定义字段
    private volatile Map<String, String> remoteOneStepContext = Collections.synchronizedMap(new TransferContext());        // 远端的context, client span 表示要发到远端的context, server span 表示从远端接收的context
    private volatile Map<String, String> localOneStepContext = Collections.synchronizedMap(new TransferContext());         // 本地的context, server span 表示本地线程put的context, 需要传递给client span的remote context
    private volatile Map<String, String> foreverContext = Collections.synchronizedMap(new TransferContext());              // 一直传递的context
    private String extend = "";                                     // 拓展字段
    private WeakReference<Span> parent = null;
    private List<Span> children = null;
    boolean virtual = false;


    /**
     * Span Constructor
     *
     * @param traceId 作为server接收请求时，从网络字节流中收到的TraceId
     * @param spanId  作为server接收请求时，从网络字节流中收到的SpanId
     */
    public Span(String traceId, String spanId, String spanName) {
        init();
        Validate.notBlank(traceId, "TraceId can't be null");
        this.traceId = traceId;
        this.spanId = Validate.isBlank(spanId) ? String.valueOf(0) : spanId;
        this.spanName = spanName;
    }


    public Span(String spanName) {
        init();
        this.traceId = String.valueOf(IdGen.get());
        this.spanId = String.valueOf(0);
        this.spanName = spanName;
    }

    public Span() {

    }

    private void init() {
        IConfig config = ConfigSingleton.getConfig();
        String cell = config.get(Tracer.CELL);
        if (cell != null && !foreverContext.containsKey(Tracer.CELL)) {
            try {
                foreverContext.put(Tracer.CELL, cell);
            } catch (Exception e) {
                // ignore
            }
        }
        String localSwimlane = config.getLocalSwimlane();
        if (localSwimlane != null && !foreverContext.containsKey(Tracer.SWIMLANE)) {
            try {
                foreverContext.put(Tracer.SWIMLANE, localSwimlane);
            } catch (Exception e) {
                // ignore
            }
        }
    }


    public void addKvAnnotation(KVAnnotation kvAnnotation) {
        if (kvAnnotations == null) {
            synchronized (this) {
                if (kvAnnotations == null) {
                    kvAnnotations = Collections.synchronizedList(new LimitLinkedList<KVAnnotation>(ANNOTATION_MAX_SIZE));
                }
            }
        }
        kvAnnotations.add(kvAnnotation);
    }


    public void setLocal(String appKey, String ip, int port) {
        if (Validate.notBlank(appKey)) {
            this.local.setAppkey(appKey);
        }
        if (Validate.notBlank(ip)) {
            this.local.setHost(ip);
        }
        this.local.setPort(port);
    }

    public void setLocalIp(String ip) {
        local.setHost(ip);
    }

    public String getLocalIp() {
        return local.getHost();
    }

    public void setLocalAppKey(String appkey) {
        local.setAppkey(appkey);
    }

    public void setLocalPort(int port) {
        local.setPort(port);
    }

    public String getLocalAppKey() {
        return local.getAppkey();
    }

    @Deprecated
    public String getLocalHost() {
        return local.getHost();
    }

    public int getLocalPort() {
        return local.getPort();
    }


    public void setRemote(String appKey, String ip, int port) {
        if (Validate.notBlank(appKey)) {
            this.remote.setAppkey(appKey);
        }
        if (Validate.notBlank(ip)) {
            this.remote.setHost(ip);
        }
        this.remote.setPort(port);
    }

    public void setRemoteAppKey(String appKey) {
        remote.setAppkey(appKey);
    }

    public void setRemoteIp(String ip) {
        remote.setHost(ip);
    }

    public void setRemotePort(int port) {
        remote.setPort(port);
    }

    public String getRemoteAppKey() {
        return remote.getAppkey();
    }

    @Deprecated
    public String getRemoteHost() {
        return remote.getHost();
    }

    public String getRemoteIp() {
        return remote.getHost();
    }


    public int getRemotePort() {
        return remote.getPort();
    }

    public int getCost() {
        return (int) (end - start);
    }

    public Span getParent() {
        if (parent == null) {
            return null;
        }
        return parent.get();
    }


    public synchronized void addChildren(Span child) {
        if (this.children == null) {
            this.children = new LinkedList<Span>();
        }
        this.children.add(child);
    }

    public void setParent(Span parent) {
        this.parent = new WeakReference<Span>(parent);
    }


    int nextSpanNum() {
        return currentSpanNum.incrementAndGet();
    }


    public enum SIDE {

        CLIENT(0), SERVER(1);

        private int value;

        SIDE(int i) {
            this.value = i;
        }

        public int getValue() {
            return value;
        }
    }
}
