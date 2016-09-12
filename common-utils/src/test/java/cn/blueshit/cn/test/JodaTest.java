package cn.blueshit.cn.test;

import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by zhaoheng on 16/8/22.
 */
public class JodaTest {

    private static void printf(Object test) {
        System.out.println(test);

    }

    public String test(String test) {
        if (test.equals("a")) {
            printf("a");
            return "a";
        } else if (test.equals("test")) {
            printf("test");
            return "test";
        } else {
            return "default";
        }
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
        System.out.println(dateTime.isAfter(dt6));

        Date checkinTime = DateUtils.truncate(new Date(System.currentTimeMillis()), Calendar.DATE);
        System.out.println(checkinTime.getTime());
        printf(new DateTime(checkinTime).plusDays(1).getEra());
        String testStr = "test";
        String checkInTime = "20160905";
        String checkOutTime = "20160906";
        DateTimeFormatter yyyyMMdd = DateTimeFormat.forPattern("yyyyMMdd");
        DateTime in = yyyyMMdd.parseDateTime(checkInTime);
        DateTime out = yyyyMMdd.parseDateTime(checkOutTime);
        printf(in.toString(yyyyMMdd));
        Date date1 = DateUtils.addDays(out.toDate(), -1);
        DateTime dateTime2 = out.minusDays(1);
        printf("in+" + in + "out+" + out + "date1+" + date1+"out:"+dateTime2);


    }
}
