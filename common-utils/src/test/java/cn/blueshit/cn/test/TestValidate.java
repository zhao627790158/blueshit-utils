package cn.blueshit.cn.test;

import cn.blueshit.cn.test.bean.Customer;
import cn.zh.blueshit.common.ParamValidator;
import cn.zh.blueshit.exception.OperateException;
import cn.zh.blueshit.https.DesCoder;
import cn.zh.blueshit.security.tool.RandomKeys;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created by zhaoheng on 2016/6/22.
 */
public class TestValidate {


    @org.junit.Test
    public void testValidate() throws OperateException {
        Customer customer = new Customer();
        customer.setCustomerId(10);
        ParamValidator.validate(customer,null);
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
