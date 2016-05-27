package cn.blueshit.cn.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;

/**
 * Created by zhaoheng on 2016/5/27.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-config-test.xml")
public class Test {


    @org.junit.Test
    public void getVerifyCode(){
        int a =1;
        System.out.println("fdsfd");
        assertNotNull(a);
    }

}
