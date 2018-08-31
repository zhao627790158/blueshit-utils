package cn.zh.blueshit.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetSocketAddress;

import static cn.zh.blueshit.common.ParamValidator.logger;

/**
 * Created by zhaoheng on 18/8/31.
 */
public class RpcNettyClient {


    public static void main(String[] args) throws Exception {
        Bootstrap b = new Bootstrap();
        b.group(new NioEventLoopGroup(1))
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new LoggingHandler(LogLevel.DEBUG))
                .handler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline channelPipeline = socketChannel.pipeline();
                        int maxLength = Integer.MAX_VALUE;
                        channelPipeline.addLast(new LengthFieldBasedFrameDecoder(maxLength, 0, 4, 0, 0));
                        channelPipeline.addLast(new RpcEncoder(RpcRequest.class));
                        channelPipeline.addLast(new RpcDecoder(RpcResponse.class));
                        channelPipeline.addLast("logger", new LoggingHandler(LogLevel.DEBUG));
                        channelPipeline.addLast(new ClientRpcHandler());

                    }
                });
        ChannelFuture channelFuture = b.connect(new InetSocketAddress("127.0.0.1", 8080));
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(final ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()) {
                    ClientRpcHandler handler = channelFuture.channel().pipeline().get(ClientRpcHandler.class);

                    System.out.println("client connected");
                    RpcRequest request = new RpcRequest();
                    request.setRequestId("1");
                    request.setClassName("myClass");
                    request.setMethodName("method1");
                    request.setParameters(new Object[]{1, 2, 3, 4});
                    request.setParameterTypes(new Class[]{int.class, int.class});
                    channelFuture.channel().writeAndFlush(request);
                } else {
                    System.out.println("server attemp failed");
                    channelFuture.cause().printStackTrace();
                }
            }

        });

        Thread.sleep(10000);
        channelFuture.await();

    }
}
