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
        System.out.println(libDir.getParentFile().getAbsolutePath() + "||" + libDir.getParentFile().getPath());

        File parentFile = libDir.getParentFile();
        System.out.println(parentFile.isDirectory());
        find(parentFile.getPath(), 1);

    }

    public static void find(String pathName, int depth) throws IOException {
        //获取pathName的File对象
        File dirFile = new File(pathName);
        //判断该文件或目录是否存在，不存在时在控制台输出提醒
        if (!dirFile.exists()) {
            System.out.println("do not exit");
            return;
        }
        for (int j = 0; j < depth; j++) {
            System.out.print("  ");
        }
        System.out.print("|--");
        System.out.println(dirFile.getName());
        //获取此目录下的所有文件名与目录名
        String[] fileList = dirFile.list();
        int currentDepth = depth + 1;
        for (int i = 0; i < fileList.length; i++) {
            //遍历文件目录
            String string = fileList[i];
            //File("documentName","fileName")是File的另一个构造器
            File file = new File(dirFile.getPath(), string);
            String name = file.getName();
            //如果是一个目录，搜索深度depth++，输出目录名后，进行递归
            if (file.isDirectory()) {
                //递归
                find(file.getCanonicalPath(), currentDepth);
            } else {
                //如果是文件，则直接输出文件名
                for (int j = 0; j < currentDepth; j++) {
                    System.out.print("   ");
                }
                System.out.print("|--");
                System.out.println(name);

            }
        }
    }

    public static boolean isLibDir(File libDir) {
        return libDir.getName().equals("lib") && libDir.getParentFile().getName().equals("WEB-INF");
    }

}
