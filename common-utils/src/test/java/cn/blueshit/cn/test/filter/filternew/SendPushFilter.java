package cn.blueshit.cn.test.filter.filternew;

import cn.blueshit.cn.test.filter.PushContext;

/**
 * Created by zhaoheng on 18/2/24.
 * 最后一个filter用来实际操作
 */
public class SendPushFilter implements PushFilterNew {

    @Override
    public boolean invokeCanInterrupt(ServiceFilterHandler serviceFilterHandler, PushContext pushContext) throws Throwable {
        if (pushContext.isStop()) {
            return false;
        }
        System.out.println("开始真正的操作 send..");
        return true;
    }
}
