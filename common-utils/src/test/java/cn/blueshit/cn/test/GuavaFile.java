package cn.blueshit.cn.test;

import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 * Created by zhaoheng on 17/1/1.
 */
public class GuavaFile {

    public static void main(String[] args) throws IOException {
        File testFile = new File("test.txt");
        Files.touch(testFile);
        Files.write("myFileTest".getBytes(), testFile);
       /* List<String> lines = Files.readLines(testFile, Charsets.UTF_8);
        for (String line : lines) {
            System.out.println(line);
        }

        Files.copy(testFile, new File("test.2"));
        testFile.deleteOnExit();
        new File("test.2").deleteOnExit();
        Files.createParentDirs(testFile);*/
        Path path = testFile.toPath();
        //file:///Users/zhaoheng/IdeaProjects/mygithub/blueshit-utils/common-utils/test.txt
        System.out.println(path.toUri());
        //file:/Users/zhaoheng/IdeaProjects/mygithub/blueshit-utils/common-utils/test.txt
        System.out.println(path.toUri().toURL());
        System.setProperty("test", "value11");


        Thread tt = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.currentThread().sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.print(System.getProperty("test"));
            }
        });
        tt.setDaemon(true);
        tt.start();
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.currentThread().sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }));

        Path path1 = FileSystems.getDefault().getPath(separators("user/test111.txt"));
        System.out.println(path1.toAbsolutePath());
        System.out.println(GuavaFile.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        System.out.println(GuavaFile.class.getProtectionDomain().getCodeSource().getLocation().getPath());

    }

    private static String separators(String path) {
        StringBuilder ret = new StringBuilder();
        for (char c : path.toCharArray()) {
            if ((c == '/') || (c == '\\')) {
                ret.append(File.separatorChar);
            } else {
                ret.append(c);
            }
        }
        return ret.toString();
    }

}
