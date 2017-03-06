package cn.blueshit.cn.test.nio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class OrdinaryServer {

    public static void main(String[] args) throws Exception {
        //创建socket服务,打开并监听8888端口
        ServerSocket server = new ServerSocket(8080);

        while (true) {
            //获取一个套接字（阻塞）
            final Socket socket = server.accept();

            System.out.println("来个一个新客户！");
            handler(socket);

        }

    }

    /**
     * 读取数据
     *
     * @param socket
     * @throws Exception
     */

    public static void handler(Socket socket) {

        try {

            byte[] bytes = new byte[1024];

            InputStream inputStream = socket.getInputStream();

            while (true) {
                //循环读取读取数据（阻塞）
                int read = inputStream.read(bytes);

                if (read != -1) {

                    System.out.println(new String(bytes, 0, read));

                } else {

                    break;

                }

            }

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            try {

                socket.close();

            } catch (IOException e) {

                e.printStackTrace();

            }

        }

    }

}