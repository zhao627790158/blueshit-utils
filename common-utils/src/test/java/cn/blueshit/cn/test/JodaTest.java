package cn.blueshit.cn.test;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.util.Date;

/**
 * Created by zhaoheng on 16/8/22.
 */
public class JodaTest {

    private static void printf(Object test) {
        System.out.println(test);

    }

    public static void main(String[] args) {

        DateTime dateTime = new DateTime(2016, 12, 13, 18, 23, 55);
        Date d = new Date();
        DateTime dateTime1 = new DateTime(d);
        dateTime = dateTime.plusDays(1) // 增加天
                .plusYears(1)// 增加年
                .plusMonths(1)// 增加月
                .plusWeeks(1)// 增加星期
                .minusMillis(1)// 减分钟
                .minusHours(1)// 减小时
                .minusSeconds(1);// 减秒数

        printf(dateTime.toString("yyyy-MM-dd HH:mm:ss"));
        printf(dateTime.isBefore(dateTime1));
        printf(Days.daysBetween(dateTime, dateTime1).getDays());

        // DateTime与java.util.Date对象,当前系统TimeMillis转换
        DateTime dt6 = new DateTime(new Date());
        Date date = dateTime1.toDate();
        DateTime dt7 = new DateTime(System.currentTimeMillis());
        dateTime1.getMillis();
        

    }
}
