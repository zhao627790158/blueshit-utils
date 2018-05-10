package cn.blueshit.cn.test.btrace;

import java.util.Random;

public class CaseObjectMain {

    public static void main(String[] args) throws Exception {
        Random random = new Random();
        CaseObject object = new CaseObject();
        while (true) {
            boolean result = object.execute(random.nextInt(1000));
            Thread.sleep(1000);
        }
    }
}