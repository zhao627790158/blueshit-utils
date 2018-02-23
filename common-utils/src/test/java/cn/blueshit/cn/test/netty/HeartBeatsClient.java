package cn.blueshit.cn.test.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class HeartBeatsClient {

    public void connect(int port, String host) throws Exception {
        // Configure the client.
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            p.addLast("ping", new IdleStateHandler(0, 4, 0, TimeUnit.SECONDS));
                            p.addLast("decoder", new StringDecoder());
                            p.addLast("encoder", new StringEncoder());
                            p.addLast(new HeartBeatClientHandler());
                        }
                    });

            ChannelFuture future = b.connect(host, port).sync();
            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        int port = 8080;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                // 采用默认值  
            }
        }
        new HeartBeatsClient().connect(port, "127.0.0.1");
    }


    static class HeartBeatClientHandler extends ChannelInboundHandlerAdapter {


        private static final ByteBuf HEARTBEAT_SEQUENCE = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("Heartbeat",
                CharsetUtil.UTF_8));

        private static final int TRY_TIMES = 3;

        private int currentTime = 0;

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("激活时间是：" + new Date());
            System.out.println("HeartBeatClientHandler channelActive");
            ctx.fireChannelActive();
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("停止时间是：" + new Date());
            System.out.println("HeartBeatClientHandler channelInactive");
        }

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            System.out.println("循环触发时间：" + new Date());
            if (evt instanceof IdleStateEvent) {
                IdleStateEvent event = (IdleStateEvent) evt;
                if (event.state() == IdleState.WRITER_IDLE) {
                    if (currentTime <= TRY_TIMES) {
                        System.out.println("currentTime:" + currentTime);
                        currentTime++;
                        ctx.channel().writeAndFlush(HEARTBEAT_SEQUENCE.duplicate());
                    }
                }
            }
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            String message = (String) msg;
            System.out.println(message);
            if (message.equals("Heartbeat")) {
                ctx.write("has read message from server");
                ctx.flush();
            }
            ReferenceCountUtil.release(msg);
        }
    }
}  

