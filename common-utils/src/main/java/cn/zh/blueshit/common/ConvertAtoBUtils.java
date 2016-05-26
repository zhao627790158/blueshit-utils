package cn.zh.blueshit.common;


import net.sf.cglib.beans.BeanCopier;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhaoheng on 2016/5/26.
 * 属性拷贝工具类
 * 比beanUtils,propertyUtils工具类快 N倍
 *
 */
public class ConvertAtoBUtils {

    //缓存BeanCopier
    static final Map<String, BeanCopier> beanCopierMap = new ConcurrentHashMap<String, BeanCopier>();


    public static <F, T> T convert(F fromBean, T toBean) {
        BeanCopier beanCopierInstance = getBeanCopierInstance(fromBean.getClass(), toBean.getClass());
        beanCopierInstance.copy(fromBean, toBean, null);
        return toBean;
    }


    public static BeanCopier getBeanCopierInstance(Class<?> fromClazz, Class<?> toClazz) {
        String key = genKey(fromClazz, toClazz);
        BeanCopier copier = null;
        if (!beanCopierMap.containsKey(key)) {
            copier = BeanCopier.create(fromClazz, toClazz, false);
            beanCopierMap.put(key, copier);
        } else {
            copier = beanCopierMap.get(key);
        }
        return copier;
    }


    private static String genKey(Class<?> srcClazz, Class<?> destClazz) {
        return srcClazz.getName() + destClazz.getName();
    }

}
