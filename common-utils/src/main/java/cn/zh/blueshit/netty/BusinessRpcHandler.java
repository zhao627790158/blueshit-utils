package cn.zh.blueshit.netty;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by zhaoheng on 18/8/31.
 */
public class BusinessRpcHandler extends SimpleChannelInboundHandler<RpcRequest> {


    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, RpcRequest rpcRequest) throws Exception {
        System.out.println("接受到请求: " + JSON.toJSONString(rpcRequest));
        RpcResponse rpcResponse = new RpcResponse();
        rpcResponse.setRequestId(rpcRequest.getRequestId());
        rpcResponse.setError("");
        rpcResponse.setResult(Lists.newArrayList(1, 2, 3));
        channelHandlerContext.writeAndFlush(rpcResponse).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                System.out.println("Send response for request " + rpcRequest.getRequestId());
            }
        });
    }
}
