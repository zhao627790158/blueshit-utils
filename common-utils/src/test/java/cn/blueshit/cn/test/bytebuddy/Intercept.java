package cn.blueshit.cn.test.bytebuddy;

import net.bytebuddy.implementation.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class Intercept {


    @RuntimeType
    public static Object intercept(@This Object proxy, @Argument(0) String arg, @Origin Method method,
                                   @SuperCall Callable<?> callable) {
        if (arg != null && arg.length() == 0) {
            return null;
        }
        try {
            //调用父方法
            return callable.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}