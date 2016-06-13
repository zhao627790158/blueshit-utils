package cn.blueshit.cn.test;

import cn.blueshit.cn.test.bean.Man;
import cn.zh.blueshit.common.BeanUtils;
import cn.zh.blueshit.common.FastReflectUtils;
import cn.zh.blueshit.common.MapUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

/**
 * Created by zhaoheng on 2016/6/13.
 */
public class TestReflect{

    @org.junit.Test
    public void testRe() throws Exception {
       Man man =new Man();
       /*  Object test = FastReflectUtils.execute("test", man, new Object[]{"1", 1, new Date()});
        System.out.println(test);*/

        Method test = ReflectionUtils.findMethod(Man.class, "test", new Class[]{String.class, int.class, Date.class});
        ReflectionUtils.invokeMethod(test, man, new Object[]{"1", 1, new Date()});
        Field userName = FastReflectUtils.findField(Man.class, "userName");
        FastReflectUtils.makeAccessible(userName);
        FastReflectUtils.setField(userName,man,"1223");
        System.out.print(man);
        Method[] allDeclaredMethods = FastReflectUtils.getAllDeclaredMethods(man.getClass());
        for (Method method : allDeclaredMethods) {
            System.out.println(method.toString());
        }
        Map map = BeanUtils.convertObject2Map(man);
        String userName1 = MapUtils.getString(map, "userName");


    }

}
