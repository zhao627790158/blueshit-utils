import com.sun.btrace.BTraceUtils;
import com.sun.btrace.annotations.*;

import java.lang.reflect.Field;

import static com.sun.btrace.BTraceUtils.Strings.str;
import static com.sun.btrace.BTraceUtils.Strings.strcat;
import static com.sun.btrace.BTraceUtils.println;

@BTrace
public class DruidBtrace {

    @OnMethod(
            clazz = "com.xxxxx.service.impl.OrderForSettlementServiceImpl",
            method = "getOrderDetailByOrderIds4Settlement",
            location = @Location(Kind.ENTRY)
    )
    public static void traceCacheBlock(@Self Object self) {
        println("DruidDataSource methods :");
        println(strcat("调用者是：", str(self)));
        Field removeAbandonedCount = BTraceUtils.field("com.alibaba.druid.pool.DruidDataSource", "removeAbandonedCount");
        Object o = BTraceUtils.get(removeAbandonedCount, self);
        println(strcat("removeAbandonedCount: ", str(o)));
    }
}
