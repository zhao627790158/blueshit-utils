package cn.blueshit.cn.test.filter;

/**
 * Created by zhaoheng on 18/2/1.
 */
public class PushTest {


    public static void main(String[] args) {

        ServiceFilterHander serviceFilterHander = new ServiceFilterHander()
                .addFilter(new PushCatMonitorFilter())
                .addFilter(new DefaultPushFilter())
                .addFilter(new PushCatMonitorFilter());
        PushContext pushContext = new PushContext();

        if(serviceFilterHander.hander(pushContext)){
            System.out.println("execute");
        }

    }
}
