package cn.blueshit.cn.test.filter;

/**
 * Created by zhaoheng on 18/2/1.
 */
public interface PushFilter {

    boolean invokeCanInterrupt(ServiceFilterHander serviceFilterHander, PushContext pushContext);
}
