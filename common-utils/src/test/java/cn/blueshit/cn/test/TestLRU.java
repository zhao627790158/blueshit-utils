package cn.blueshit.cn.test;

import cn.zh.blueshit.common.LRUCache;

import java.util.Map;
import java.util.Set;

/**
 * Created by zhaoheng on 2016/7/11.
 */
public class TestLRU {

    @org.junit.Test
    public void testLru() {
        LRUCache<Integer, String> cache = new LRUCache<Integer, String>(10);
        for (int i = 0; i < 10; i++) {
            cache.put(i, "test" + i);
        }
        cache.get(0);
        cache.put(11, "test11");
        Set<Map.Entry<Integer, String>> entries = cache.entrySet();
        for (Map.Entry<Integer, String> map : entries) {
            System.out.println("key: " + map.getKey() + "---value: " + map.getValue());
        }


    }


    @org.junit.Test
    public void test() {
        try {
            int i = 1 / 0;
        } catch (RuntimeException e) {
            System.out.println("1");
            throw new RuntimeException("22");
        } catch (Exception e) {
            System.out.println("22");
        }
    }

}
