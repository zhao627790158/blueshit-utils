package cn.blueshit.cn.test.filter;

import com.google.common.collect.Maps;
import lombok.Data;

import java.util.HashMap;

/**
 * Created by zhaoheng on 18/2/1.
 */
@Data
public class PushContext<K, V> {

    private boolean stop;

    private HashMap<K, V> contextMap = Maps.newHashMap();


    public void addData(K key, V value) {
        contextMap.put(key, value);
    }


    public void removeDada(K key) {
        contextMap.remove(key);
    }

    public void immediatelyStop() {
        setStop(true);
    }


}
