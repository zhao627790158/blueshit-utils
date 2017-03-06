package cn.blueshit.cn.test.runtime;

import java.io.*;

/**
 * Created by zhaoheng on 17/2/12.
 */
public class LinuxGrepTest {

    public static void main(String[] args) throws IOException, InterruptedException {
        //-c参数是告诉它读取随后的字符串，而最后的参数是你要运行的脚本。
        String[] cmd = new String[]{"/bin/sh", "-c", "ls|grep test"};
        String[] commands = new
                String[]{"find", ".", "-name", "*mysql*", "-print"};

        Process exec = Runtime.getRuntime().exec(cmd);

        InputStream inputStream = exec.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        System.out.println(sb.toString());
        //System.out.println(runScript("cat -n  " + sb.toString()));

    }

    public static String runScript(String cmd) {
        StringBuffer buf = new StringBuffer();
        String rt = "-1";
        try {
            Process pos = Runtime.getRuntime().exec(cmd);
            pos.waitFor();
            InputStreamReader ir = new InputStreamReader(pos.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            String ln = "";
            while ((ln = input.readLine()) != null) {
                buf.append(ln + "\n");
            }
            rt = buf.toString();
            input.close();
            ir.close();
        } catch (java.io.IOException e) {
            rt = e.toString();
        } catch (Exception e) {
            rt = e.toString();
        }
        return rt;
    }
}
