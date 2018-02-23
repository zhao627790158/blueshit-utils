package cn.blueshit.cn.test.filter;

/**
 * Created by zhaoheng on 18/2/1.
 */
public class DefaultPushFilter implements PushFilter {

    @Override
    public boolean invokeCanInterrupt(ServiceFilterHander serviceFilterHander, PushContext pushContext) {
        System.out.println("DefaultPushFilter log start");
        boolean hander = serviceFilterHander.hander(pushContext);
        System.out.println("DefaultPushFilter log end");
        return hander;
    }

}
