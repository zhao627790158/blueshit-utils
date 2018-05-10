package cn.blueshit.cn.test.log;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zhaoheng on 18/4/16.
 */
public class LogTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogTest.class);


    @Test
    public void test() {

        LOGGER.debug("test", "tr");

        LOGGER.info("test", "tr");

        LOGGER.error("test", new RuntimeException("DFDFD"));


    }

}
