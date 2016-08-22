package cn.blueshit.cn.test.command;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import java.net.URLEncoder;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhaoheng on 16/8/17.
 */
@Slf4j
public class TestGson {


    private static void printf(Object test) {
        System.out.println(test);
    }

    @Test
    public void test6() throws Exception {
        System.out.println(URLEncoder.encode("%#=", "utf-8"));
        System.out.println(UUID.randomUUID().toString().replace("-", ""));

        ArrayList<Integer> integers = Lists.newArrayList(1, 2, 3, 4, 5, 6);
        String test = JSON.toJSONString(integers);
        System.out.println(test);
        List<Integer> list = JSON.parseObject(test, new TypeReference<List<Integer>>(){}.getType());
        printf(list.size());

    }

    @Test
    public void test5() {
        Stopwatch started = Stopwatch.createStarted();
        System.out.println(RandomStringUtils.randomAlphabetic(32));
        System.out.print(started.elapsed(TimeUnit.MILLISECONDS) + "\n");
        Stopwatch startedTwo = Stopwatch.createStarted();
        System.out.println(RandomStringUtils.randomAlphanumeric(32));
        System.out.println(startedTwo.elapsed(TimeUnit.MILLISECONDS));

        HashMap<String, Object> objectObjectHashMap = Maps.newHashMap();
        for (int i = 0; i < 2; i++) {
            objectObjectHashMap.put(Integer.toString(i), Integer.toString(i));
        }
        objectObjectHashMap.put("test", new Date().getTime());
        String join = Joiner.on(" ").withKeyValueSeparator("=").join(objectObjectHashMap);
        System.out.println(join);
    }


    @Test
    public void test1() {
        List<Integer> objects = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            objects.add(i);
        }
        System.out.println(Joiner.on(",,").join(objects));
        ArrayList<String> strings = Lists.newArrayList("request_uuid", "user_id", "request_time", "method_name");
        System.out.println(Joiner.on("= ").join(strings).toString());


    }

    @Test
    public void test2() {
        JSONObject errorJson = new JSONObject();
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
        log.info("testParse:{}", stringListMap);


    }

    @Test
    public void test3() throws ParseException {
        Date date1 = new Date();//60998400
        Date date2 = DateUtils.parseDate("2014-09-11", "yyyy-MM-dd");
        Date dt1 = DateUtils.truncate(date1, Calendar.DATE);
        Date dt2 = DateUtils.truncate(date2, Calendar.DATE);
        //log.info("testTime--:{},data1:{},data2:{},{}", dt1, dt2, FastMath.toIntExact(Math.abs((dt1.getTime() - dt2.getTime()) / 1000)));

        log.info("{}", Math.floor(1.512));
        log.info("{}", Math.round(1.512));
    }

    @Test
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
            @Override
            public String apply(Person input) {
                return input.getId();
            }
        });
        System.out.println("将主键当作Map的Key:" + JSON.toJSONString(personMap));
        // 转换Map中的value值
        Map<String, String> transformValuesMap = Maps.transformValues(personMap, new Function<Person, String>() {
            @Override
            public String apply(Person input) {
                return input.getName();
            }
        });
        String testLine = JSON.toJSONString(transformValuesMap);
        System.out.println("转换Map中的value值" + testLine);
        Map map = JSON.parseObject(testLine, new TypeReference<Map<String, String>>() {
        }.getType());
        log.info("--" + map);
    }

    public static enum test {
        ONE, TWO;
    }

    class Person {
        private String Id;
        @JSONField(name = "my_name")
        private String name;

        private List<String> list = Lists.newArrayList();

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

        public List<String> getList() {
            return list;
        }

        public void setList(List<String> list) {
            this.list = list;
        }
    }


}
