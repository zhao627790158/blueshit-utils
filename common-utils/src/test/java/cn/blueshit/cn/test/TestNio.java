package cn.blueshit.cn.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by zhaoheng on 2016/6/12.
 */
public class TestNio {


    @org.junit.Test
    public void copyFile() throws Exception {
        String infile = "d:\\test.txt";
        String outfile = "d:\\copy.txt";
        FileInputStream fin = new FileInputStream(new File(infile));
        FileOutputStream fout = new FileOutputStream(outfile);
        //使用通道
        // 获取输入输出通道
        FileChannel fcin = fin.getChannel();
        FileChannel fcout = fout.getChannel();
        //申请缓冲
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        while (fcin.read(buffer) != -1) {
            buffer.flip();
            fcout.write(buffer);
            buffer.clear();
        }
    }




}
