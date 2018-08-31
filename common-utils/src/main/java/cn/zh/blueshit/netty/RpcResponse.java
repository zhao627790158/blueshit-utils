package cn.zh.blueshit.netty;

import lombok.Data;

/**
 * Created by zhaoheng on 18/8/31.
 */
@Data
public class RpcResponse {

    private String requestId;
    private String error;
    private Object result;


}
