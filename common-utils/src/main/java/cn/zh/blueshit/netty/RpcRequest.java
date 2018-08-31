package cn.zh.blueshit.netty;

import lombok.Data;

/**
 * Created by zhaoheng on 18/8/31.
 */
@Data
public class RpcRequest {

    private String requestId;
    private String className;
    private String methodName;
    private Class<?>[] parameterTypes;
    private Object[] parameters;


}
