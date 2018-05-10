import com.sun.btrace.annotations.*;

import static com.sun.btrace.BTraceUtils.Strings.str;
import static com.sun.btrace.BTraceUtils.Strings.strcat;
import static com.sun.btrace.BTraceUtils.println;

@BTrace
public class Tracetest {

    @OnMethod(
            clazz = "com.meituan.hotel.order.thrift.service.impl.OrderForSettlementServiceImpl",
            method = "getOrderDetailByOrderIds4Settlement",
            location = @Location(Kind.ENTRY)
    )
    public static void traceCacheBlock(@Self Object self) {
        println("DruidDataSource methods :");
        println(strcat("调用者是：", str(self)));
    }
}

