package cn.zh.blueshit.db;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;

/**
 * Created by zhaoheng on 2016/5/20.
 */
public class RouteUtils {

    private static final Logger log = LoggerFactory.getLogger(RouteUtils.class);


    /**
     * 默认编码
     */
    private final static String encode = "utf-8";
    /**
     * 最大资源数
     */
    private final static int resourceMax = 10000;

    /**
     * 获取hashCode
     *
     * @param routeValue
     * @return
     */
    public static int getHashCodeBase64(String routeValue) {
        int hashCode = 0;
        try {
            byte[] pinBase64 = Base64.decodeBase64(routeValue.getBytes(encode));
            hashCode = Math.abs(new String(pinBase64, encode).hashCode());
        } catch (Exception e) {
            log.error("hashCode 失败", e);
        }
        return hashCode;
    }

    /**
     * 获取资源码
     *
     * @param routeValue
     * @return
     */
    public static int getResourceCode(String routeValue) {
        int hashCode = RouteUtils.getHashCodeBase64(routeValue);
        int resourceCode = hashCode % resourceMax;
        return resourceCode;
    }


    public static void main(String args[]) {
        String test ="avia";

        int hashCodeBase64 = RouteUtils.getHashCodeBase64(test);
        System.out.println(hashCodeBase64);

        int hashCode = 22231;
        int dbNumber = 10;
        int tableNumber =5;
        int mode = dbNumber*tableNumber;//取余 绝对比 乘积要小
        int dbIndex = hashCode % mode / tableNumber;
        int tableIndex = hashCode%mode/dbNumber;
        System.out.println(dbIndex);

        DecimalFormat df = new DecimalFormat();
        String    style = "_0000";//在格式后添加诸如单位等字符
        df.applyPattern(style);
        String test11 = df.format(tableIndex);

        System.out.println(test11);//_004

    }
}
