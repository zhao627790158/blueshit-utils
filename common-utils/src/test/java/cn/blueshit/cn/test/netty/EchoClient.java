package cn.blueshit.cn.test.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;

/**
 * Created by zhaoheng on 17/5/2.
 */
public class EchoClient {
    private final String host;
    private final int port;

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }


    public static void main(String[] args) throws Exception {

        new EchoClient("127.0.0.1", 8080).start();
    }

    public void start() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.remoteAddress(new InetSocketAddress(host, port));
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {

                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new EchoClientHandler());
                }
            });
            ChannelFuture f = bootstrap.connect().sync();

            f.addListener(new ChannelFutureListener() {

                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()) {
                        System.out.println("client connected");
                    } else {
                        System.out.println("server attemp failed");
                        future.cause().printStackTrace();
                    }

                }
            });
            f.channel().closeFuture().sync();
        } catch (Exception ex) {

        } finally {
            group.shutdownGracefully().sync();
        }
    }

    @ChannelHandler.Sharable
    class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
        /**
         * 此方法会在连接到服务器后被调用
         */
        public void channelActive(ChannelHandlerContext ctx) {
            ctx.write(Unpooled.copiedBuffer("Netty rocks!", CharsetUtil.UTF_8));
        }

        /**
         * 此方法会在接收到服务器数据后调用
         */
        public void channelRead0(ChannelHandlerContext ctx, ByteBuf in) {
            System.out.println("Client received: " + ByteBufUtil.hexDump(in.readBytes(in.readableBytes())));
        }

        /**
         * 捕捉到异常
         */
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            cause.printStackTrace();
            ctx.close();
        }

    }
}
