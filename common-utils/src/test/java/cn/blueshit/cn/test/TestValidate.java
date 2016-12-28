package cn.blueshit.cn.test;

import cn.blueshit.cn.test.bean.Customer;
import cn.zh.blueshit.common.ParamValidator;
import cn.zh.blueshit.exception.OperateException;
import cn.zh.blueshit.https.DesCoder;
import cn.zh.blueshit.security.tool.RandomKeys;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by zhaoheng on 2016/6/22.
 */
public class TestValidate {

    public void test111() {
        //请记住PECS原则：生产者（Producer）使用extends，消费者（Consumer）使用super。
        //extends 主要是可以控制读时的类型
        List<? extends Number> foo3 = new ArrayList<Integer>();
        Number number = foo3.get(1);//get 来消费数据

        //super可以控制写入时的类型
        List<? super Integer> foo4 = new ArrayList<Integer>();
        foo4.add(new Integer(1));//add操作  生成数据

    }


    @org.junit.Test
    public void test() {
        Customer customer = new Customer();
        customer.setCustomerId(10);
        Customer customer2 = new Customer();
        customer2.setCustomerId(20);
        Customer customer3 = new Customer();
        customer3.setCustomerId(5);
        List<Customer> list = Lists.newArrayList(customer, customer2, customer3);

        for (Customer c : list) {
            System.out.println(c.getCustomerId());
        }
        Collections.sort(list, new Comparator<Customer>() {
            @Override
            public int compare(Customer o1, Customer o2) {
                return o1.getCustomerId() - o2.getCustomerId();
            }
        });
        for (Customer c : list) {
            System.out.println(c.getCustomerId());
        }
      /*  CollectionUtils.filter(list, new Predicate<Customer>() {
            @Override
            public boolean evaluate(Customer object) {
                return StringUtils.isNotBlank(object.getCustomerName());
            }
        });*/

        List<String> tilteList = Lists.newArrayList("旅游", "接送", "nn");
        String keyTitle = "点击查看礼包详情";
        if (CollectionUtils.isNotEmpty(tilteList)) {
            switch (tilteList.size()) {
                case 1:
                    keyTitle = "";
                    keyTitle = tilteList.get(0);
                    break;
                case 2:
                    keyTitle = "";
                    keyTitle = tilteList.get(0) + "、" + tilteList.get(1);
                    break;
                default:
                    keyTitle = "";
                    keyTitle = tilteList.get(0) + "、" + tilteList.get(1) + "等";
                    break;
            }
        }
        System.out.println(keyTitle);

    }

    @org.junit.Test
    public void testValidate() throws OperateException {
        Customer customer = new Customer();
        customer.setCustomerId(10);
        ParamValidator.validate(customer, null);
    }

    @org.junit.Test
    public void testAes() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String password = RandomKeys.initKey();
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed("dsq5o99kfzwjv5s7anc9akjdl85nwf2b".getBytes());

        kgen.init(128, secureRandom);

        SecretKey secretKey = kgen.generateKey();

        byte[] enCodeFormat = secretKey.getEncoded();
        System.out.println(DesCoder.showByteArray(enCodeFormat));
    }


}
