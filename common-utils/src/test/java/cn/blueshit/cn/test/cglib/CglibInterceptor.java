package cn.blueshit.cn.test.cglib;

import com.google.common.base.Optional;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.apache.ibatis.annotations.ResultType;
import org.junit.Test;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTextTabStop;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by zhaoheng on 18/2/22.
 */
public class CglibInterceptor implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("before:" + method.toString());
        Object object = methodProxy.invokeSuper(o, objects);
        System.out.println("after:" + method.toString());
        return object;
    }

    public static void main(String[] args) throws Exception {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(CglibTestInterface.class);
        enhancer.setCallback(new CglibInterceptor());
        CglibTestInterface cglibTestInterface = (CglibTestInterface) enhancer.create();
        cglibTestInterface.getContest("trtr");
        cglibTestInterface.getContest2("trtr");
        System.out.println(CglibTestInterface.class.getProtectionDomain().getClassLoader());
        System.out.println(CglibTestInterface.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        System.out.println(CglibTestInterface.class.getResource(""));
        System.out.println(CglibTestInterface.class.getResource("/"));
        System.out.println(Thread.currentThread().getContextClassLoader().getResource(""));
        File file = new File(Thread.currentThread().getContextClassLoader().getResource("").toURI());
        System.out.println(file.isDirectory());
        getDirectory(file);
    }


    // 递归遍历
    private static void getDirectory(File file) {
        File flist[] = file.listFiles();
        if (flist == null || flist.length == 0) {
        }
        for (File f : flist) {
            if (f.isDirectory()) {
                //这里将列出所有的文件夹
                System.out.println("Dir==>" + f.getAbsolutePath());
                getDirectory(f);
            } else {
                //这里将列出所有的文件
                System.out.println("file==>" + f.getAbsolutePath());
            }
        }
    }

    @Test
    public void test() {
        Optional<Integer> test = Optional.fromNullable(12).or(Optional.of(2));
        if (test.isPresent()) {
            System.out.println(test.get());
        } else {
            System.out.println(Optional.absent());
        }

    }
}
