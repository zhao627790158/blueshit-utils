package cn.blueshit.cn.test;

import cn.blueshit.cn.test.bean.Man;
import cn.zh.blueshit.common.BeanUtils;
import cn.zh.blueshit.common.FastReflectUtils;
import cn.zh.blueshit.common.MapUtils;
import cn.zh.blueshit.common.UnicodeUtil;
import cn.zh.blueshit.security.HMACSHA1Utils;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaoheng on 2016/6/13.
 */
public class TestReflect {

    @org.junit.Test
    public void testRe() throws Exception {
        Man man = new Man();
       /*  Object test = FastReflectUtils.execute("test", man, new Object[]{"1", 1, new Date()});
        System.out.println(test);*/

        Method test = ReflectionUtils.findMethod(Man.class, "test", new Class[]{String.class, int.class, Date.class});
        ReflectionUtils.invokeMethod(test, man, new Object[]{"1", 1, new Date()});
        Field userName = FastReflectUtils.findField(Man.class, "userName");
        FastReflectUtils.makeAccessible(userName);
        FastReflectUtils.setField(userName, man, "1223");
        System.out.print(man);
       /* Method[] allDeclaredMethods = FastReflectUtils.getAllDeclaredMethods(man.getClass());
        for (Method method : allDeclaredMethods) {
            System.out.println(method.toString());
        }*/
        Map map = BeanUtils.convertObject2Map(man);
        String userName1 = MapUtils.getString(map, "userName");

        String[] test1 = new String[]{"1", "2", "3", "4"};
        String[] strings = Arrays.copyOf(test1, test1.length > 3 ? 3 : test1.length);
        System.out.println(test1.length);

        for (String s : strings) {
            System.out.println(s);
        }
        System.out.println(strings.length);
        String nu = "\\u8bf7\\u6c42\\u5931\\u8d25\\uff0c\\u53c2\\u6570\\u9519\\u8bef";
        System.out.println(nu);
        System.out.println(UnicodeUtil.fromUnicodeString(nu));
    }


    @org.junit.Test
    public void testSha1() throws NoSuchAlgorithmException, InvalidKeyException {
        String secretKey = "Gu5t9xGARNpq86cd98joQYCN3Cozk1qA";
        String srcStr = "GETcvm.api.qcloud.com/v2/index.php?Action=DescribeInstances&Nonce=345122&Region=gz&SecretId=AKIDz8krbsJ5yKBZQpn74WFkmLPx3gnPhESA&Timestamp=1408704141";
        String signature = HMACSHA1Utils.getSignature(srcStr.getBytes(), secretKey.getBytes());
        System.out.println(signature);
    }

    @org.junit.Test
    public void testGson() {
        List<Integer> objects = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            objects.add(i);
        }
        System.out.println(Joiner.on(",,").join(objects));
    }

}
