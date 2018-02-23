package cn.blueshit.cn.test.current;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadLocalTest {

    public static void main(String[] args) {

        MyThreadPoolExecutor pool = new MyThreadPoolExecutor(2, 2, 10,
                TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(1000));

        int i = 0;
        while (true) {
            final int loop = i++;
            // 进行set的Runnable
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    MyThreadLocal.currentAgentId.set(1);
                    System.out.println(loop + "=1==" + Thread.currentThread()
                            + "seted currentAgentId:"
                            + MyThreadLocal.currentAgentId.get());
                }
            });

            pool.execute(new Runnable() {
                @Override
                public void run() {
                    System.err.println(loop + "=2==" + Thread.currentThread()
                            + "seted currentAgentId:"
                            + MyThreadLocal.currentAgentId.get());
                }
            });

            pool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(loop + "=3==" + Thread.currentThread()
                            + "seted currentAgentId:"
                            + MyThreadLocal.currentAgentId.get());
                }
            });

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.err.println("================");
        }
        // pool.shutdown();
    }

}

class MyThreadPoolExecutor extends ThreadPoolExecutor {

    public MyThreadPoolExecutor(int i, int j, int k, TimeUnit seconds,
                                ArrayBlockingQueue<Runnable> arrayBlockingQueue) {
        super(i, j, k, seconds, arrayBlockingQueue);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        //任务执行回调可以作为重置操作
        System.out.println("-------" + t.getName());
        MyThreadLocal.currentAgentId.set(888);
    }

    protected void afterExecute(Runnable r, Throwable t) {
        //任务执行回调可以作为重置操作
        MyThreadLocal.currentAgentId.set(null);
    }

}

abstract class MyThreadLocal {
    public static ThreadLocal<Integer> currentAgentId = new ThreadLocal<Integer>();
}
