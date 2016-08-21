package cn.blueshit.cn.test.current;

import com.google.common.base.Stopwatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhaoheng on 16/8/20.
 */
public class ConuntDownLatchDemo {

    public long timeTask(int nThreads, final Runnable runnable) {

        final CountDownLatch start = new CountDownLatch(1);
        final CountDownLatch end = new CountDownLatch(nThreads);

        for (int i = 0; i < nThreads; i++) {
            Thread t = new Thread() {
                @Override
                public void run() {
                    try {
                        start.await();
                        try {
                            System.out.print("do something");
                        } finally {
                            end.countDown();
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            };
            t.start();
        }
        Stopwatch started = Stopwatch.createStarted();
        start.countDown();
        try {
            end.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return started.elapsed(TimeUnit.MILLISECONDS);
    }

}
