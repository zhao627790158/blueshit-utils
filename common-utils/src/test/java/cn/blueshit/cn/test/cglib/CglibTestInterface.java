package cn.blueshit.cn.test.cglib;

/**
 * Created by zhaoheng on 18/2/22.
 */
public class CglibTestInterface {


    public String getContest(String str) {
        System.out.println(String.format("getContest 1:{%s}", str));
        return str;

    }

    public String getContest2(String str) {
        System.out.println(String.format("getContest 2:{%s}", str));
        return str;

    }

    public static void main(String[] args) {
        System.out.println(new CglibTestInterface().getContest("test"));
    }

}
