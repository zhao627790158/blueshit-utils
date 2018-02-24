package cn.blueshit.cn.test.filter.filternew;

import cn.blueshit.cn.test.filter.PushContext;

/**
 * Created by zhaoheng on 18/2/24.
 */
public interface PushFilterNew {

    boolean invokeCanInterrupt(ServiceFilterHandler serviceFilterHandler, PushContext pushContext) throws Throwable;


}
