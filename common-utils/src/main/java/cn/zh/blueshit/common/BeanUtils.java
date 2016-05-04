package cn.zh.blueshit.common;


import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by zhaoheng on 2016/4/25.
 */
public class BeanUtils {
    private static final Logger logger = LoggerFactory.getLogger(BeanUtils.class);

    public BeanUtils() {
    }


    public static Map convertObject2Map(Object obj) {
        try {
            return org.apache.commons.beanutils.BeanUtils.describe(obj);
        } catch (Exception var2) {
            logger.error("将对象转换为map时发生异常",var2);
            throw new RuntimeException("将对象转换为map时发生异常", var2);
        }
    }
    public static void copyProperties(Object dest, Object orig) {
        try {
            PropertyUtils.copyProperties(dest, orig);
        } catch (Exception var3) {
            logger.error(var3.getMessage());
            throw new RuntimeException("对象属性复制时出现异常!", var3);
        }
    }

}
