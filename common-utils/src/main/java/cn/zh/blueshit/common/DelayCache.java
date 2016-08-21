package cn.zh.blueshit.common;

import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by zhaoheng on 16/8/21.
 * 对象只能在其到期时才能从队列中取走。这种队列是有序的，
 */
public class DelayCache<K, V> {
    public ConcurrentHashMap<K, V> map = new ConcurrentHashMap<K, V>();
    public DelayQueue<DelayedItem<K>> queue = new DelayQueue<DelayedItem<K>>();


    public void put(K k, V v, long liveTime) {
        V v2 = map.put(k, v);//返回前一个key对应的值
        DelayedItem<K> kDelayedItem = new DelayedItem<K>(k, liveTime);
        if (v2 != null) {
            queue.remove(kDelayedItem);
        }
        queue.put(kDelayedItem);
    }

    public DelayCache() {
        //当缓存关闭，监控程序也应关闭，因而监控线程应当用守护线程
        Thread t = new Thread() {
            @Override
            public void run() {
                dameonCheckOverdueKey();
            }
        };
        //设置为守护线程
        t.setDaemon(true);
        t.start();
    }

    public void dameonCheckOverdueKey() {
        while (true) {
            DelayedItem<K> delayedItem = queue.poll();//弹出head,只有到期的才会弹出
            if (delayedItem != null) {
                map.remove(delayedItem.getT());
                System.out.println(" remove " + delayedItem.getT() + " from cache");
            }
            try {
                Thread.sleep(300);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }

    }

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        int cacheNumber = 10;
        int liveTime = 0;
        DelayCache<String, Integer> cache = new DelayCache<String, Integer>();

        for (int i = 0; i < cacheNumber; i++) {
            liveTime = random.nextInt(3000);
            System.out.println(i + "  " + liveTime);
            cache.put(i + "", i, random.nextInt(liveTime));
            if (random.nextInt(cacheNumber) > 7) {
                liveTime = random.nextInt(3000);
                System.out.println(i + "  " + liveTime);
                cache.put(i + "", i, random.nextInt(liveTime));
            }
        }

        Thread.sleep(3000);
        System.out.println();
    }
}

class DelayedItem<T> implements Delayed {
    private T t;
    private long liveTime;
    private long removeTime;

    public DelayedItem(T t, long liveTime) {
        this.setT(t);
        this.liveTime = liveTime;
        this.removeTime = TimeUnit.NANOSECONDS.convert(liveTime, TimeUnit.NANOSECONDS) + System.nanoTime();
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(removeTime - System.nanoTime(), unit);
    }

    @Override
    public int compareTo(Delayed o) {
        if (o == null) return 1;
        if (o == this) return 0;
        if (o instanceof DelayedItem) {
            DelayedItem<T> tmpDelayedItem = (DelayedItem<T>) o;
            if (liveTime > tmpDelayedItem.liveTime) {
                return 1;
            } else if (liveTime == tmpDelayedItem.liveTime) {
                return 0;
            } else {
                return -1;
            }
        }
        long diff = getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS);
        return diff > 0 ? 1 : diff == 0 ? 0 : -1;
    }


    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    public long getLiveTime() {
        return liveTime;
    }

    public void setLiveTime(long liveTime) {
        this.liveTime = liveTime;
    }

    public long getRemoveTime() {
        return removeTime;
    }

    public void setRemoveTime(long removeTime) {
        this.removeTime = removeTime;
    }

    @Override
    public int hashCode() {
        return t.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof DelayedItem) {
            return object.hashCode() == hashCode() ? true : false;
        }
        return false;
    }
}
