package cn.zh.blueshit.current.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * Created by zhaoheng on 2016/6/3.
 */
public abstract class BaseCallable<T> implements Callable<T> {

    private CountDownLatch latch;

    /**
     * 线程超时时间，默认为3000ms
     */
    private long timeout = 3000;

    public T call() throws Exception {
        T o = null;
        try {
            o = execute();
        } catch (Exception e) {
            throw e;
        } finally {
            latch.countDown();
        }
        return o;
    }

    public abstract T execute() throws Exception;

    public CountDownLatch getLatch() {
        return latch;
    }

    public void setLatch(CountDownLatch latch) {
        this.latch = latch;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

}
