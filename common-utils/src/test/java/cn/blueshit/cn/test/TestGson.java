package cn.blueshit.cn.test;

import cn.blueshit.cn.test.bean.User;
import cn.zh.blueshit.common.GsonUtils;

/**
 * Created by zhaoheng on 2016/6/9.
 */
public class TestGson {

    @org.junit.Test
    public  void testGson() {
        User user = new User();

        user.setUserName("blueShit senior ");
        long t1 = System.currentTimeMillis();
        for(int i=0;i<100000;i++) {
            user.setUserId(i);
           System.out.print(GsonUtils.toJson(user)+"\n");
        }
        System.out.println(System.currentTimeMillis()-t1);

    }
}
