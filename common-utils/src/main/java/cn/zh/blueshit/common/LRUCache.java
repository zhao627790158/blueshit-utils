package cn.zh.blueshit.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by zhaoheng on 2016/7/11.
 * 利用LinkedHashMap实现简单的缓存， 必须实现removeEldestEntry方法
 * 按插入顺序的链表，和按访问顺序(调用get方法)的链表。
 * 默认是按插入顺序排序，如果指定按访问顺序排序，那么调用get方法后，会将这次访问的元素移至链表尾部，不断访问可以形成按访问顺序排序的链表
 * 可以重写removeEldestEntry方法返回true值指定插入元素时移除最老的元素
 */
public class LRUCache<K, V> extends LinkedHashMap<K, V> {

    private final int maxCapacity;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    private final Lock lock = new ReentrantLock();


    public LRUCache(int maxCapacity) {
        /**
         * * @param  accessOrder     the ordering mode - <tt>true</tt> for access-order, <tt>false</tt> for insertion-order
         * true表示按照访问顺序排序
         */
        super(maxCapacity, DEFAULT_LOAD_FACTOR, true);
        this.maxCapacity = maxCapacity;
    }

    //his method is invoked by put and putAll after inserting a new entry into the map.
    @Override
    protected boolean removeEldestEntry(java.util.Map.Entry<K, V> eldest) {
        try {
            lock.lock();
            return size() > maxCapacity;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean containsKey(Object key) {
        try {
            lock.lock();
            return super.containsKey(key);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean containsValue(Object value) {
        try {
            lock.lock();
            return super.containsValue(value);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public V get(Object key) {
        try {
            lock.lock();
            return super.get(key);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public V put(K key, V value) {
        try {
            lock.lock();
            return super.put(key, value);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        try {
            lock.lock();
            super.putAll(m);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int size() {
        try {
            lock.lock();
            return super.size();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean isEmpty() {
        try {
            lock.lock();
            return super.isEmpty();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public V remove(Object key) {
        try {
            lock.lock();
            return super.remove(key);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void clear() {
        try {
            lock.lock();
            super.clear();
        } finally {
            lock.unlock();
        }
    }

    public Collection<Map.Entry<K, V>> getAll() {
        try {
            lock.lock();
            return new ArrayList<Map.Entry<K, V>>(super.entrySet());
        } finally {
            lock.unlock();
        }
    }


}
