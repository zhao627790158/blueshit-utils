package cn.blueshit.cn.test.command;

import cn.blueshit.cn.test.bean.TestBean;
import cn.blueshit.cn.test.bean.User;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * Created by zhaoheng on 16/8/17.
 */
@Slf4j
public class TestGson {


    @Test
    public void test12() {
        JSONObject jsonObject = JSONObject.parseObject("{\n" +
                "    \"status\": \"success\",\n" +
                "    \"data\": {\n" +
                "        \"money_detail\": {\n" +
                "            \"title\": \"退款金额\",\n" +
                "            \"contents\": \"<span>798元</span>\"\n" +
                "        },\n" +
                "        \"account_detail\": {\n" +
                "            \"title\": \"退回账户\",\n" +
                "            \"contents\": [\n" +
                "                \"美团余额\"\n" +
                "            ]\n" +
                "        },\n" +
                "        \"time_detail\": {\n" +
                "            \"title\": \"到账时间\",\n" +
                "            \"contents\": \"已到账\"\n" +
                "        },\n" +
                "        \"detail\": [\n" +
                "            [\n" +
                "                {\n" +
                "                    \"title\": \"美团审核通过\",\n" +
                "                    \"text\": null,\n" +
                "                    \"time\": \"2015-11-11 15:34:58\",\n" +
                "                    \"status\": 2\n" +
                "                },\n" +
                "                {\n" +
                "                    \"title\": \"已到账\",\n" +
                "                    \"text\": null,\n" +
                "                    \"time\": \"2015-11-13 11:34:26\",\n" +
                "                    \"status\": 2\n" +
                "                }\n" +
                "            ]\n" +
                "        ],\n" +
                "        \"promo_detail\": null\n" +
                "    }\n" +
                "}");
        System.out.println(jsonObject.getJSONObject("data").getJSONArray("detail").getJSONArray(0).getJSONObject(0).getString("time"));
    }


    @Test
    public void test11() {
        String test = String.format("%s  %d", 1, 1);
        System.out.println(test);
        Person p1 = new Person("001", "zhang_san");
        Person p2 = new Person("002", "li_si");
        Map<Integer, Person> datePriceMap = Maps.newHashMap();
        datePriceMap.put(20161101, p1);
        datePriceMap.put(20161102, p2);
        Iterable<String> transform = Iterables.transform(datePriceMap.entrySet(), new Function<Map.Entry<Integer, Person>, String>() {
            @Override
            public String apply(Map.Entry<Integer, Person> input) {
                return input.getKey().toString();
            }
        });
        Iterator<String> iterator = transform.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    private static void printf(Object test) {
        System.out.println(test);
    }

    public List change(List list) {
        //list = Lists.newArrayList();
        list.add("2");
        return list;
    }

    @Test
    public void test9() {
        User user = null;
        testRepace(user);
        System.out.println(user);
    }

    public void testRepace(User user) {
        user = new User(200);
    }

    public static boolean validateMobilePhoneNumber(String phone) {
        Pattern p = Pattern.compile("[0-9]{11}");
        return p.matcher(phone).find();
    }

    @Test
    public void test8() {
        final List<User> users = Lists.newArrayList();
        User user = new User(100);
        User user1 = new User(200);
        users.add(user);
        users.add(user1);
        testUser(users);

        final List<User> users1 = Lists.newArrayList();
        users1.addAll(users);

        printf(users.size());


    }

    public static void testUser(List<User> users) {

        final List<Integer> cons = Collections.emptyList();
        CollectionUtils.filter(users, new Predicate<User>() {
            @Override
            public boolean evaluate(User object) {
                return cons.contains(object.getUserId());
            }
        });

    }

    @Test
    public void test7() {
        List<String> list = new ArrayList<String>();
        printf(List.class.isAssignableFrom(List.class));
        printf(list.getClass().isAssignableFrom(List.class));
        printf(list.getClass().isAssignableFrom(ArrayList.class));
        List<String> test1 = Lists.newArrayList("1", "2");
        change(test1);
        System.out.print(test1.size());
    }

    @Test
    public void test6() throws Exception {
        System.out.println(URLEncoder.encode("%#=", "utf-8"));
        System.out.println(UUID.randomUUID().toString().replace("-", ""));

        ArrayList<Integer> integers = Lists.newArrayList(1, 2, 3, 4, 5, 6);
        String test = JSON.toJSONString(integers);
        System.out.println(test);
        List<Integer> list = JSON.parseObject(test, new TypeReference<List<Integer>>() {
        }.getType());
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
    public void test21() throws IllegalAccessException {
        int i = 0;
        TestBean testBean = new TestBean();
        Field[] declaredFields = testBean.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            field.setInt(testBean, i++);
        }
        String s = JSON.toJSONString(testBean);
        System.out.println(s);
        ParserConfig.getGlobalInstance().setAsmEnable(false);
        TestBean testBean1 = JSON.parseObject(s, TestBean.class);
        System.out.println(testBean1);
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
        final List<Integer> integers = Lists.newArrayList(2, 3, 8);

        //在list2也包含的
        CollectionUtils.filter(list, new Predicate<Integer>() {
            @Override
            public boolean evaluate(Integer object) {
                return integers.contains(object.intValue());
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

    @Data
    @AllArgsConstructor
    class B {
        private A a;
    }

    @Data
    class A {
        private B b;
    }

    @Test
    public void test43() {
        A a = new A();
        B b = new B(a);
        a.setB(b);
        System.out.println(JSON.toJSONString(a, SerializerFeature.DisableCircularReferenceDetect));
    }


}
