package cn.blueshit.cn.test.current;

import com.google.common.base.Stopwatch;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhaoheng on 16/8/21.
 */
public class TestDealayQueue {

    private static DelayQueue<DelayElement> starterQueue = new DelayQueue<DelayElement>();

/*

    try {
        starterQueue.take();*/


    public static void main(String[] args) {
        starterQueue.add(new DelayElement<String>("1", 1000, TimeUnit.MILLISECONDS));
        starterQueue.add(new DelayElement<String>("2", 2000, TimeUnit.MILLISECONDS));
        starterQueue.add(new DelayElement<String>("3", 3000, TimeUnit.MILLISECONDS));
        starterQueue.add(new DelayElement<String>("4", 5000, TimeUnit.MILLISECONDS));
        Stopwatch started = Stopwatch.createStarted();
        try {
            System.out.println("begin");
            while (!starterQueue.isEmpty()) {
                System.out.println(starterQueue.take().getT());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(started.elapsed(TimeUnit.MILLISECONDS));
    }

    private static class DelayElement<T> implements Delayed {

        private T t;

        private long delayTime;

        private long createdTime = System.nanoTime();

        public DelayElement(T t, long delayTime, TimeUnit unit) {
            this.setT(t);
            this.delayTime = unit.toNanos(delayTime);
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(delayTime - (System.nanoTime() - createdTime), TimeUnit.NANOSECONDS);
        }

        @Override
        public int compareTo(Delayed other) {
            if (other == this)
                return 0;
            if (other instanceof DelayElement) {
                DelayElement x = (DelayElement) other;
                long diff = delayTime - x.delayTime;
                if (diff < 0)
                    return -1;
                else if (diff > 0)
                    return 1;
                else
                    return 1;
            }
            long d = (getDelay(TimeUnit.NANOSECONDS) - other.getDelay(TimeUnit.NANOSECONDS));
            return (d == 0) ? 0 : ((d < 0) ? -1 : 1);
        }

        public T getT() {
            return t;
        }

        public void setT(T t) {
            this.t = t;
        }

        public long getDelayTime() {
            return delayTime;
        }

        public void setDelayTime(long delayTime) {
            this.delayTime = delayTime;
        }

        public long getCreatedTime() {
            return createdTime;
        }

        public void setCreatedTime(long createdTime) {
            this.createdTime = createdTime;
        }
    }
}
