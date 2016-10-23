package cn.blueshit.cn.test.nio;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zhaoheng on 16/10/23.
 * <p>
 * telnet 127.0.0.1 8080
 */
public class ServerTest {

    private static Charset charset = Charset.forName("UTF-8");

    public static void main(String[] args) {
        server();
    }

    public static void server() {
        AtomicInteger atomicInteger = new AtomicInteger();
        ServerSocketChannel channel = null;

        try {
            Selector selector = Selector.open();
            channel = ServerSocketChannel.open();
            channel.configureBlocking(false);
            channel.socket().setReuseAddress(true);
            channel.bind(new InetSocketAddress(8080));
            channel.register(selector, SelectionKey.OP_ACCEPT, new Integer(1));
            while (true) {
                if (selector.select() > 0) {
                    Set<SelectionKey> sets = selector.selectedKeys();
                    Iterator<SelectionKey> keys = sets.iterator();
                    while (keys.hasNext()) {
                        SelectionKey key = keys.next();
                        keys.remove();
                        if (key.isAcceptable()) {
                            key.attach(new Integer(1));
                            SocketChannel schannel = ((ServerSocketChannel) key.channel()).accept();
                            //设置成非堵塞
                            schannel.configureBlocking(false);
                            //如果你对不止一种事件感兴趣，那么可以用“位或”操作符将常量连接起来
                            schannel.register(selector, SelectionKey.OP_READ);
                            System.out.println("接受到客户端链接:" + atomicInteger.incrementAndGet());
                        }
                        if (key.isReadable()) {
                            SocketChannel schannel = (SocketChannel) key.channel();
                            ByteBuffer buf = ByteBuffer.allocate(10);
                            int read = schannel.read(buf);
                            if (read > 0) {
                                byte[] data = buf.array();
                                String msg = new String(data).trim();
                                System.out.println("服务端收到信息：" + msg);
                                //广播给其他在线客户端
                                //BroadCast(selector, schannel, ("\nserver:" + msg + "\n"));
                                //只回复给自己
                                schannel.write(charset.encode(("\nserver:" + msg + "\n")));
                            } else {
                                System.out.println("客户端关闭");
                                key.cancel();
                            }
                           /* ByteArrayOutputStream output = new ByteArrayOutputStream();
                            int len = 0;
                            while ((len = schannel.read(buf)) > 0) {
                                //倒转成读模式
                                buf.flip();
                                byte by[] = new byte[buf.remaining()];
                                buf.get(by);
                                output.write(by);
                                buf.clear();
                            }
                            String str = new String(output.toByteArray()).trim();
                            key.attach(str);
                            System.out.println("read....." + str);*/

                        }

                        /*
                        * 3.key.OP_WRITE事件神马时候注册呢？
                        *答：其实真正写的时候很少用，其实WRITE主要描述底层缓存区是否有空间，
                        * 当然正常的时候缓冲区都是存在足够的空间的，如果存在空间就返回true
                        * 干掉下面的
                        * */
                        /*if (key.isWritable()) {
                            System.out.println("write.....");
                            Thread.sleep(2000);
                            //获取到key上附件
                            Object object = key.attachment();
                            String attach = object != null ? "server replay: " + object.toString() : "server replay: ";
                            SocketChannel schannel = (SocketChannel) key.channel();
                            schannel.write(ByteBuffer.wrap(attach.getBytes()));
                        }*/
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (channel != null) {
                try {
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static void BroadCast(Selector selector, SocketChannel except, String content) throws IOException {
        //广播数据到所有的SocketChannel中
        for (SelectionKey key : selector.keys()) {
            Channel targetchannel = key.channel();
            //如果except不为空，不回发给发送此内容的客户端
            if (targetchannel instanceof SocketChannel && targetchannel != except) {
                SocketChannel dest = (SocketChannel) targetchannel;
                dest.write(charset.encode(content));
            }
        }
    }
}
