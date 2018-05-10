package cn.blueshit.cn.test.btrace;

import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.Kind;
import com.sun.btrace.annotations.Location;
import com.sun.btrace.annotations.OnMethod;

import static com.sun.btrace.BTraceUtils.jstack;
import static com.sun.btrace.BTraceUtils.println;

/**
 * Created by zhaoheng on 18/3/13.
 */
@BTrace
public class TracingScript {

    @OnMethod(
            clazz = "cn.blueshit.cn.test.btrace.btrace.CaseObject",
            method = "/.*/"
    )
    public static void traceCacheBlock() {
        println("Who call java.util.zip.Inflater's methods :");
        jstack();
    }
}
