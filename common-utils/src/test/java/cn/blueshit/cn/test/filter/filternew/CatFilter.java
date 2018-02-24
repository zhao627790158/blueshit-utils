package cn.blueshit.cn.test.filter.filternew;

import cn.blueshit.cn.test.filter.PushContext;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zhaoheng on 18/2/24.
 */
public class CatFilter implements PushFilterNew {

    private static AtomicInteger testInt = new AtomicInteger();

    @Override
    public boolean invokeCanInterrupt(ServiceFilterHandler serviceFilterHandler, PushContext pushContext) throws Throwable {
        if (pushContext.isStop()) {
            return false;
        }
        int a = testInt.incrementAndGet();
      /*  if (a == 3) {
            pushContext.setStop(true);
        }*/
        System.out.println("cat start log" + a);
        boolean handle = serviceFilterHandler.handle(pushContext);
        System.out.println("cat end log" + a);
        return handle;
    }
}
