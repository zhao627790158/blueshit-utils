package cn.blueshit.cn.test;

import cn.blueshit.cn.test.bean.Man;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertNotNull;

/**
 * Created by zhaoheng on 2016/5/27.
 */
/*@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-config-test.xml")*/
public class Test {

    @Value("${xiaozhaoheng}")
    private String testName;


    @org.junit.Test
    public void testMan() {
        Man man = new Man();
        Man man1 = new Man();
        int a = 10;
        int b = 1111233;
        int i = a ^ b;
        System.out.println(i << 10);
        System.out.println(i ^ b);
        final ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        concurrentHashMap.put("key1", "vale1");
        concurrentHashMap.get("key1");
        System.out.println("key1".hashCode());
    }


    /*hash可能会溢出 为负数*/
    @org.junit.Test
    public void testHashValue() throws UnsupportedEncodingException {
        String str = "任意字符串";//unicode 符号集
        str = new String(str.getBytes("utf-8"));// 按照UTF-8来找到相应的二进制存储方式
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 200000; i++) {
            stringBuilder.append(str);
        }
        int a = stringBuilder.toString().hashCode();
        System.out.println(a);
        System.out.print(Integer.MAX_VALUE);

    }


    @org.junit.Test
    public void testB() throws UnsupportedEncodingException {
        System.out.println(testName);
        String a = "-1195";//输入数值
        BigInteger src = new BigInteger(a);//转换为BigInteger类型
        System.out.println(src.toString(2));//转换为2进制并输出结果

        a = "-10010101011";
        BigInteger big = new BigInteger(a, 2);

        System.out.println(big.toString());

        System.out.println(Integer.valueOf("10101", 2).toString());

        String test = "一一";
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 2; i++) {
            stringBuilder.append(test);
        }

        System.out.println(stringBuilder.toString().hashCode() + "-hash");
        System.out.println(new BigInteger(String.valueOf(Long.MAX_VALUE)).toString() + "----||" + Long.MAX_VALUE);
        byte[] gbks = test.getBytes("GBK");
        byte[] bytes = test.getBytes("utf-8");
        System.out.println(gbks.length);
        System.out.println(bytes.length);
        for (int i = 0; i < gbks.length; i++) {
            System.out.print(Integer.toBinaryString(gbks[i] & 0xff));
        }
        System.out.print("\n");

        for (int i = 0; i < bytes.length; i++) {
            System.out.print(Integer.toBinaryString(bytes[i] & 0xff));
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
