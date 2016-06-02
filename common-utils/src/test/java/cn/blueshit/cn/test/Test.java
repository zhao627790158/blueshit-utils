package cn.blueshit.cn.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertNotNull;

/**
 * Created by zhaoheng on 2016/5/27.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-config-test.xml")
public class Test {


    @org.junit.Test
    public void getVerifyCode(){
         final Pattern PATTERN = Pattern.compile("^(\\d+\\.\\d+\\.\\d+\\.\\d+):(\\d+),(\\d+)\\|(\\d+)$");
        int a =1;
        System.out.println("fdsfd");
        assertNotNull(a);
        String data = "192.168.150.78:20130,0|1";

        Matcher matcher = PATTERN.matcher(data);
        if(matcher.find()){
            matcher.group(1);
            Long.valueOf(matcher.group(3)).longValue();
        }else{
            System.out.println("ccccccccc");
        }



    }

}
