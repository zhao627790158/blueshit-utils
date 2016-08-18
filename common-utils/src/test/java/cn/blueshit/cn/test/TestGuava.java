package cn.blueshit.cn.test;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by zhaoheng on 16/8/18.
 */
@Slf4j
public class TestGuava {

    @org.junit.Test
    public void test1() throws ParseException {
        List<Integer> objects = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            objects.add(i);
        }
        System.out.println(Joiner.on(",,").join(objects));

       /* JSONObject errorJson = new JSONObject();
        JSONObject content = new JSONObject();
        content.put("code", null);
        content.put("domain", "mobile-api");
        content.put("type", "type_1");
        errorJson.put("error", content);
        Object o = errorJson;
        JSONObject o1 = (JSONObject) JSON.toJSON(o);
        log.info("----:{}", JSON.toJSONString(o1.get("error")));

        for (Map.Entry<String, Object> entry : ((JSONObject) o1.get("error")).entrySet()) {
            log.info("key:{},value:{}", entry.getKey(), entry.getValue());
        }

        ArrayList<Integer> integers = Lists.newArrayList(1, 2, 3, 4, 5, 6);
        HashMap<String, List<Integer>> listHashMap = Maps.newHashMap();
        listHashMap.put("key", integers);
        String mapJson = JSON.toJSONString(listHashMap);
        log.info("mapJson:{}", mapJson);
        Map<String, List<Integer>> stringListMap = JSON.parseObject(mapJson, new TypeReference<Map<String, List<Integer>>>() {
        }.getType());
        log.info("testParse:{}", stringListMap);*/
        Date date1 = new Date();//60998400
        Date date2 = DateUtils.parseDate("2014-09-11", "yyyy-MM-dd");
        Date dt1 = DateUtils.truncate(date1, Calendar.DATE);
        Date dt2 = DateUtils.truncate(date2, Calendar.DATE);
      /*  log.info("testTime--:{},data1:{},data2:{},{}",dt1,dt2, FastMath.toIntExact(Math.abs((dt1.getTime() - dt2.getTime()) / 1000)));

        log.info("{}",Math.floor(1.512));
        log.info("{}", Math.round(1.512));*/
    }


    /**
     * 测试RateLimiter
     */
    @org.junit.Test
    public void test2() {
        RateLimiter limiter = RateLimiter.create(1.0); // 每秒不超过4个任务被提交
        for (int i = 0; i < 10; i++) {
            limiter.acquire(); // 请求RateLimiter, 超过permits会被阻塞
            System.out.println(i);
        }
    }

    /*@org.junit.Test
    public void test4() {
        List<Integer> list = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7);
        final List<Integer> integers = Lists.newArrayList(1, 2, 3, 8);

        //在list2也包含的
        CollectionUtils.filter(list, new Predicate<Integer>() {
            @Override
            public boolean evaluate(Integer object) {
                return integers.contains(object);
            }
        });
        log.info("结果-integers-:{}", JSON.toJSONString(integers));
        log.info("结果-list-:{}", JSON.toJSONString(list));
        List<Integer> transform = Lists.transform(integers, new Function<Integer, Integer>() {
            @Nullable
            @Override
            public Integer apply(Integer input) {
                return input * 10;
            }
        });
        log.info("结果-transform-:{}", JSON.toJSONString(transform));
        log.info("是否相等:{}", test.ONE == test.ONE);


        Person p1 = new Person("001", "zhang_san");
        Person p2 = new Person("002", "li_si");

        List<Person> personList = Lists.newArrayList();
        personList.add(p1);
        personList.add(p2);
        // 将主键当作Map的Key
        Map<String, Person> personMap = Maps.uniqueIndex(personList, new Function<Person, String>() {
            @Nullable
            @Override
            public String apply(@Nullable Person input) {
                return input.getId();
            }
        });
        System.out.println("将主键当作Map的Key:" + JSON.toJSONString(personMap));
    }*/

    public static enum test {
        ONE, TWO;
    }

    class Person {
        private String Id;
        private String name;

        public Person(String Id, String name) {
            this.Id = Id;
            this.name = name;
        }

        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


    }
}
