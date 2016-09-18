package cn.zh.blueshit.common;

import cn.zh.blueshit.transcodes.ProtostuffUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.sf.cglib.proxy.*;
import org.apache.poi.ss.formula.functions.T;

import java.io.*;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by zhaoheng on 16/9/18.
 */
public class DeepCloneUtils {


    public static <T> T deepClone(T object) throws IOException, ClassNotFoundException {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(object);

        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);
        return (T) ois.readObject();
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        List list = Lists.newArrayList();
        list.add(Lists.newArrayList(new Integer(8888)));
        /*InterfaceMaker im = new InterfaceMaker();
        im.add(Serializable.class);
        //创建一个接口
        Class aClass = im.create();
        Object o1 = Enhancer.create(DelayCache.class, new Class[]{aClass}, new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                return methodProxy.invokeSuper(o, objects);
            }
        });
        DelayCache o = (DelayCache) o1;*/
        List stringIntegerDelayCache = DeepCloneUtils.deepClone(list);
        System.out.println(list + "\n" + stringIntegerDelayCache);


    }
}
