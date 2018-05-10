package btrace;

import com.sun.btrace.BTraceUtils;
import com.sun.btrace.annotations.*;

import java.lang.reflect.Field;

import static com.sun.btrace.BTraceUtils.Strings.str;
import static com.sun.btrace.BTraceUtils.Strings.strcat;
import static com.sun.btrace.BTraceUtils.jstack;
import static com.sun.btrace.BTraceUtils.print;
import static com.sun.btrace.BTraceUtils.println;

/**
 * Created by zhaoheng on 18/3/13.
 */
@BTrace
public class TracingScript {


    public static void traceCacheBlock() {
        println("Who call java.util.zip.Inflater's methods :");
        jstack();
    }

    @OnMethod(
            clazz = "btrace.CaseObject",
            method = "/.*/",
            location = @Location(Kind.RETURN)
    )
    public static void traceExecute(@Self Object self, int sleepTime, @Return boolean result) throws Exception {
        println("调用堆栈！！");
        println(strcat("调用者是：", str(self)));
        Field sleepTotalTime = BTraceUtils.field("btrace.CaseObject", "sleepTotalTime");
        Object o = BTraceUtils.get(sleepTotalTime, self);
        print(strcat("sleepcount: ", str(o)));
        println(strcat("返回结果是：", str(result)));
        jstack();
        println(strcat("时间是：", str(sleepTime)));
    }
}
