package cn.blueshit.cn.test.bean;

import java.util.Date;

/**
 * Created by zhaoheng on 2016/6/12.
 */
public class Man extends User {

    private static Man man = new Man();
    public static Long test = 100L;

    public Man() {
        super();
        System.out.println("man................");
    }

    public void test(String name, int age, Date date) {
        System.out.println(name +"--"+age+"--"+date.toString());
    }



}
