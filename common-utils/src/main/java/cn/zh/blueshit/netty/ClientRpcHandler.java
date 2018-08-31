package cn.zh.blueshit.netty;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by zhaoheng on 18/8/31.
 */
public class ClientRpcHandler extends SimpleChannelInboundHandler<RpcResponse> {


    @Setter
    @Getter
    private NioSocketChannel channel;

    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, RpcResponse rpcResponse) throws Exception {
        System.out.println("接受到响应结果:" + JSON.toJSONString(rpcResponse));
    }
}
