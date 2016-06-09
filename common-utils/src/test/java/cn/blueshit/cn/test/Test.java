package cn.blueshit.cn.test;

import org.apache.commons.collections.CollectionUtils;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertNotNull;

/**
 * Created by zhaoheng on 2016/5/27.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-config-test.xml")
public class Test {

    @Value("${xiaozhaoheng}")
    private String testName;



    @org.junit.Test
    public void testB() throws UnsupportedEncodingException {
        System.out.println(testName);
        String a = "-1195";//输入数值
        BigInteger src = new BigInteger(a);//转换为BigInteger类型
        System.out.println(src.toString(2));//转换为2进制并输出结果

        a="-10010101011";
        BigInteger big = new BigInteger(a,2);

        System.out.println(big.toString());

        System.out.println(Integer.valueOf("10101",2).toString() );

        String test = "一";
        byte[] gbks = test.getBytes("GBK");
        byte[] bytes = test.getBytes("utf-8");
        System.out.println(gbks.length);
        System.out.println(bytes.length);
        for(int i=0; i< gbks.length ; i++){
            System.out.print(Integer.toBinaryString(gbks[i]&0xff));
        }
        System.out.print("\n");

        for(int i=0; i< bytes.length ; i++){
            System.out.print(Integer.toBinaryString(bytes[i]&0xff));
        }


    }


    @org.junit.Test
    public void getVerifyCode() {
        final Pattern PATTERN = Pattern.compile("^(\\d+\\.\\d+\\.\\d+\\.\\d+):(\\d+),(\\d+)\\|(\\d+)$");
        int a = 1;
        System.out.println("fdsfd");
        assertNotNull(a);
        String data = "192.168.150.78:20130,0|1";

        Matcher matcher = PATTERN.matcher(data);
        if (matcher.find()) {
            matcher.group(1);
            Long.valueOf(matcher.group(3)).longValue();
        } else {
            System.out.println("ccccccccc");
        }


    }

    @org.junit.Test
    public void testUnion() {

        List<String> testA = new ArrayList<String>();
        List<String> testB = new ArrayList<String>();
        for (int i = 0; i < 1000; i++) {
            if (i == 10) {
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
        for (int i = 0; cons.length > i; i++) {
            System.out.println(cons[i]);
        }

    }

}
