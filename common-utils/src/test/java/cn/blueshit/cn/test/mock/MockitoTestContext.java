package cn.blueshit.cn.test.mock;

import org.mockito.MockitoAnnotations;

/**
 * Created by zhaoheng on 17/1/30.
 */
public class MockitoTestContext {

    public MockitoTestContext() {
        MockitoAnnotations.initMocks(this);
    }
}
