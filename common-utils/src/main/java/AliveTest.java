import com.sun.btrace.annotations.*;

import static com.sun.btrace.BTraceUtils.Strings.str;
import static com.sun.btrace.BTraceUtils.Strings.strcat;
import static com.sun.btrace.BTraceUtils.println;

@BTrace
public class AliveTest {


    @OnMethod(
            clazz = "com.xxx.xpush.web.controller.AliveController",
            method = "test",
            location = @Location(Kind.ENTRY)
    )
    public static void traceCacheBlock(@Self Object self) {
        println("DruidDataSource methods :");
        println(strcat("调用者是：", str(self)));
    }

}
