package cn.blueshit.cn.test.file;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by zhaoheng on 18/5/18.
 */
public class MappedByteBufferTest {


    @Test
    public void mappedTest() throws Exception {

        File file = new File("/Users/zhaoheng/replace.sh");
        int length = (int) file.length();
        FileChannel rw = new RandomAccessFile(file, "rw").getChannel();
        MappedByteBuffer map = rw.map(FileChannel.MapMode.READ_WRITE, 0, file.length());
        byte[] data = new byte[(int) file.length()];
        for (int offset = 0; offset < length; offset++) {
            data[offset] = map.get();
        }
        System.out.println(new String(data, "utf-8"));
    }


}
