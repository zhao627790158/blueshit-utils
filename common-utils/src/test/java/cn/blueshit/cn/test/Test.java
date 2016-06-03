package cn.blueshit.cn.test;

import org.apache.commons.collections.CollectionUtils;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertNotNull;

/**
 * Created by zhaoheng on 2016/5/27.
 */
/*@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-config-test.xml")*/
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

    @org.junit.Test
    public void testUnion() {

        List<String> testA = new ArrayList<String>();
        List<String> testB = new ArrayList<String>();
        for (int i = 0; i < 1000; i++) {
            if(i==10){
                continue;
            }
            testA.add(i + "-------A");
            testB.add(i + "-------B");
        }
        System.out.println(System.currentTimeMillis());
        Collection<String> union = CollectionUtils.union(testA, testB);
        System.out.println(System.currentTimeMillis());
        for (String s : union) {
            System.out.println(s);
        }


    }

    @org.junit.Test
    public void testCuns() throws InterruptedException {

        Class<?> clazz = null;
        try {
            clazz = Class.forName("cn.blueshit.cn.test.bean.Customer");
        } catch (Exception e) {
            // TODO: handle exception
        }
        //取得所有构造函数
        Constructor<?> cons[] = clazz.getConstructors();
        for (int i=0;cons.length>i;i++){
            System.out.println(cons[i]);
        }

    }

}
