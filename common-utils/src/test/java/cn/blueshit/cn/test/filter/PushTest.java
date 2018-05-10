package cn.blueshit.cn.test.filter;

import org.junit.Test;

/**
 * Created by zhaoheng on 18/2/1.
 */
public class PushTest {

    @Test
    public void test() {

        long treePeriod = System.currentTimeMillis() / (60 * 60 * 1000L);
        long messagePeriod = (System.currentTimeMillis()- 10 * 1000L) / (3600 * 1000L); // 10 seconds extra time allowed

        if (treePeriod < messagePeriod ) {
            System.out.println("        // if (treePeriod < messagePeriod || m_length >= m_configManager.getMaxMessageChildren()) {\n");
        }

    }


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
