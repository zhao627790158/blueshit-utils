package cn.blueshit.cn.test.sql;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * Created by zhaoheng on 18/4/8.
 */
public class DbRouteTest {


    public static final int[] db = new int[4];

    public static final int[] table = new int[10];

    public static final Map<Integer, List<Integer>> dbMap = Maps.newConcurrentMap();

    public static final Map<String, List<Integer>> tableMap = Maps.newConcurrentMap();


    @Test
    public void testDbDispersion() {
        for (int i = 0; i < 100000; i++) {
            db[i % db.length] += 1;
            table[i % table.length] += 1;
        }
        System.out.println(JSON.toJSONString(db));
        System.out.println(JSON.toJSONString(table));
    }

    @Test
    public void testDbDispersionV1() {
        int temp = db.length * table.length;
        for (int i = 0; i < 100000; i++) {
            db[i % temp / table.length] += 1;
            table[i % table.length] += 1;
        }
        System.out.println(JSON.toJSONString(db));
        System.out.println(JSON.toJSONString(table));
    }

    @Test
    public void testDbDispersionV2() {
        int temp = db.length * table.length;
        for (int i = 0; i < 1000; i++) {
            int dbIndex = i % temp / table.length;
            if (!dbMap.containsKey(dbIndex)) {
                dbMap.put(dbIndex, Lists.newArrayList(i));
            } else {
                List<Integer> integers = dbMap.get(dbIndex);
                integers.add(i);
            }
            int tableIndex = i % table.length;
            if (!tableMap.containsKey(dbIndex + "_" + tableIndex)) {
                tableMap.put(dbIndex + "_" + tableIndex, Lists.newArrayList(i));
            } else {
                List<Integer> integers = tableMap.get(dbIndex + "_" + tableIndex);
                integers.add(i);
            }
        }

        System.out.println(JSON.toJSONString(dbMap));
        System.out.println(JSON.toJSONString(tableMap));
    }


}
