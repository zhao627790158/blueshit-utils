package trace;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Created by zhaoheng on 18/5/30.
 */
@Data
@NoArgsConstructor
public class TraceParam {

    private String traceId; // optional 不传会自动生成, 中间件RPC调用需要传递该值
    private String spanId; // optional 不传会自动生成
    private String spanName; // required 方法名
    private String localAppKey; // 优先取本地app.properties文件
    private String localIp; // optional 不需要填
    private int localPort; // optional
    private String remoteAppKey; // optional 远端appkey
    private String remoteIp; // optional 远端ip
    private int remotePort; // optional 远端port
    private String infraName; // optional 最好填写,判断调用的类型
    private String version; // optional 中间件的版本
    private int packageSize; // optional 请求包大小
    private boolean debug = false; // optional
    private Map<String, String> foreverContext;
    private Map<String, String> oneStepContext;

    private String extend;


    public TraceParam(String spanName) {
        this.spanName = spanName;
    }
}
