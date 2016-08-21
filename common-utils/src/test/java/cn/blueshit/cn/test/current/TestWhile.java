package cn.blueshit.cn.test.current;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhaoheng on 16/8/21.
 */
public class TestWhile {

    private Object obj = new Object();

    private volatile boolean flag = false;

    public Object getObj() {
        return this.obj;
    }

    public void state() {
        synchronized (obj) {
            while (!isNotify()) {
                try {
                    obj.wait();
                    System.out.println("execute end");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return;
            }
        }
    }

    public void note() {
        synchronized (obj) {
            obj.notify();
        }
    }
    public boolean isNotify() {
        System.err.println("test----");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return flag;
    }

    public static void main(String[] args) {
        final TestWhile testWhile = new TestWhile();
        DelayQueue delayQueue = new DelayQueue();

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    testWhile.note();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        thread.start();

        testWhile.state();

        System.out.println(TimeUnit.NANOSECONDS.convert(1000, TimeUnit.NANOSECONDS));
    }

}
