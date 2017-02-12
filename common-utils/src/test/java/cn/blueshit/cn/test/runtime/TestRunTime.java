package cn.blueshit.cn.test.runtime;

import java.io.*;

/**
 * Created by zhaoheng on 17/2/9.
 */
public class TestRunTime {
    //使用命令执行java文件,并指定依赖jar包
    //java -cp maven-artifact-3.3.9.jar:blueshit-utils.jar cn.zh.blueshit.common.VersionUtil

    public static void main(String[] args) throws IOException {


        String cmd = "pwd";
        Process exec = Runtime.getRuntime().exec(cmd);
        InputStream inputStream = exec.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line;
        String path = null;
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
            path = line;
        }
        System.out.println(new File(path).toURI().toURL());
        System.out.println(new File(path).toURI().toURL().getProtocol());
        System.out.println(new File(path).toURI().toURL().getFile());

        System.out.print("\n");
        String mmsBootPath = TestRunTime.class.getProtectionDomain().getCodeSource().getLocation().getPath();

        System.out.println(mmsBootPath);
        File libDir = (new File(mmsBootPath)).getParentFile();
        System.out.println(isLibDir(libDir) + "--" + libDir.getName() + "---:" + libDir.getParentFile().getName());

    }

    public static boolean isLibDir(File libDir) {
        return libDir.getName().equals("lib") && libDir.getParentFile().getName().equals("WEB-INF");
    }

}
