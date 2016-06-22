package cn.blueshit.cn.test;

import cn.blueshit.cn.test.bean.Customer;
import cn.zh.blueshit.common.ParamValidator;
import cn.zh.blueshit.exception.OperateException;

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




}
