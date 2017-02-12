package cn.blueshit.cn.test.mock;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Created by zhaoheng on 17/2/11.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestBeanFactory {

    private static MockService service;

    public static MockService getService() {
        return service;
    }

    public void setService(MockService service) {
        TestBeanFactory.service = service;
    }
}
