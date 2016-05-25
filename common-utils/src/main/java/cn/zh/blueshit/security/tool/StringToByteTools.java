package cn.zh.blueshit.security.tool;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by zhaoheng on 2016/5/23.
 */
public class StringToByteTools {

    public StringToByteTools() {
    }

    public static String parseByte2HexStr(byte[] buf) {
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < buf.length; ++i) {
            String hex = Integer.toHexString(buf[i] & 255);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex);
        }

        return sb.toString();
    }

    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        } else {
            byte[] result = new byte[hexStr.length() / 2];

            for (int i = 0; i < hexStr.length() / 2; ++i) {
                int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
                int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
                result[i] = (byte) (high * 16 + low);
            }

            return result;
        }
    }

    public static byte[] inputStreamTOByte(InputStream in) throws IOException {
        short BUFFER_SIZE = 4096;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[BUFFER_SIZE];
        int count1;
        while ((count1 = in.read(data, 0, BUFFER_SIZE)) != -1) {
            outStream.write(data, 0, count1);
        }
        byte[] outByte = outStream.toByteArray();
        outStream.close();
        return outByte;
    }

    public static String inputStreamTOString(InputStream inputStream) throws IOException {
        return inputStreamTOString(inputStream, "utf-8");
    }

    public static String inputStreamTOString(InputStream in, String encoding) throws IOException {
        return new String(inputStreamTOByte(in), encoding);
    }

}
