package cn.blueshit.cn.test.nio;

import com.google.common.collect.Lists;
import org.apache.commons.lang.math.RandomUtils;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.List;

/**
 * Created by zhaoheng on 16/10/23.
 * <p>
 * 1.客户端需要进行 ch.finishiCnonnect()操作,否则两边都阻塞着
 * 2.读channel中的bytebuffer时, while((len=ch.read(buffer))!=0) 判断不要写成while((len=ch.read(buffer))!=-1)
 * 如果SocketChannel被设置为非阻塞，则它的read操作可能返回三个值：
 * 1) 大于0，表示读取到了字节数；
 * 2) 等于0，没有读取到消息，可能TCP处于Keep-Alive状态，接收到的是TCP握手消息；
 * 3) -1，连接已经被对方合法关闭。
 */
public class ClientTest {


    public static void main(String[] args) throws InterruptedException {
        //client();

        List<Thread> list = Lists.newArrayList();
        for (int i = 0; i < 50; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    clientOld();
                }
            });
            list.add(thread);
            thread.start();
        }
        for (Thread thread : list) {
            thread.join();
        }
    }

    public static void clientOld() {
        Socket client = null;
        BufferedReader is = null;
        Writer writer = null;
        try {
            Thread.sleep(5000);
            client = new Socket("127.0.0.1", 8080);
            writer = new OutputStreamWriter(client.getOutputStream());
            writer.write("Hello Server.");
            writer.write("eof\n");
            writer.flush();

            is = new BufferedReader(new InputStreamReader(client.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String temp;
            int index;
            while ((temp = is.readLine()) != null) {
                if ((index = temp.indexOf("eof")) != -1) {
                    sb.append(temp.substring(0, index));
                    break;
                }
                sb.append(temp);
            }
            System.out.println(sb.toString());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (writer != null) {
                    writer.close();
                }
                if (client != null) {
                    client.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void client() {
        SocketChannel channel = null;
        try {
            Selector selector = Selector.open();
            channel = SocketChannel.open();
            channel.configureBlocking(false);
            channel.connect(new InetSocketAddress(8020));
            channel.register(selector, SelectionKey.OP_CONNECT);

            while (true) {
                //select()阻塞到至少有一个通道在你注册的事件上就绪了
                //服务端断开 这里cpu使用率会100%
                if (selector.select() > 0) {
                    Iterator<SelectionKey> set = selector.selectedKeys().iterator();
                    while (set.hasNext()) {
                        SelectionKey key = set.next();
                        set.remove();
                        SocketChannel ch = (SocketChannel) key.channel();
                        if (key.isConnectable()) {
                            ch.register(selector, SelectionKey.OP_READ, new Integer(1));
                            ch.finishConnect();
                        }
                        if (key.isReadable()) {
                            key.attach(new Integer(1));
                            ByteArrayOutputStream output = new ByteArrayOutputStream();
                            ByteBuffer buffer = ByteBuffer.allocate(1024);
                            int len = 0;
                            while ((len = ch.read(buffer)) > 0) {
                                buffer.flip();
                                byte by[] = new byte[buffer.remaining()];
                                buffer.get(by);
                                output.write(by);
                                buffer.clear();
                            }
                            System.out.println(new String(output.toByteArray()));
                            output.close();
                        }
                        /*if (key.isWritable()) {
                            key.attach(new Integer(1));
                            ch.write(ByteBuffer.wrap(("client say:hi" + RandomUtils.nextInt(10000)).getBytes()));
                        }*/
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    static class ClientRunnable implements Runnable {

        private SocketChannel ch;

        private ClientRunnable(SocketChannel ch) {
            this.ch = ch;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    ch.write(ByteBuffer.wrap((("client say:hi")).getBytes()));
                    Thread.sleep(5000);
                }
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    ch.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

}
