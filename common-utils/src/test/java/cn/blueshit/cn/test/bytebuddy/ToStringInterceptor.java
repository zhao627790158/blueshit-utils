package cn.blueshit.cn.test.bytebuddy;

import net.bytebuddy.implementation.bind.annotation.RuntimeType;

/**
 * Created by zhaoheng on 19/1/3.
 */
public class ToStringInterceptor {

    String intercept() {
        return "Hello byte buddy";
    }


}
