package cn.blueshit.cn.test.bean;

import java.util.Date;

/**
 * Created by zhaoheng on 2016/6/12.
 */
public class Man extends User {


    public void test(String name, int age, Date date) {
        System.out.println(name +"--"+age+"--"+date.toString());
    }



}
