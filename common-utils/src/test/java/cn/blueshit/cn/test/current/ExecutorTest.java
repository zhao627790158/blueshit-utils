package cn.blueshit.cn.test.current;

import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.AllArgsConstructor;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;

import java.util.concurrent.*;

/**
 * Created by zhaoheng on 17/4/9.
 */
public class ExecutorTest {


    @Test
    public void test1() {
        Thread haha = new Thread(new Runnable() {
            @Override
            public void run() {

                throw new RuntimeException("为捕获异常");
            }
        });
        haha.setName("mytest");
        haha.start();
        System.out.println("这里还是会被执行");
    }

    public class UehLogger implements Thread.UncaughtExceptionHandler {

        @Override
        public void uncaughtException(Thread t, Throwable e) {
            System.out.println(t.getName() + "----" + e);
        }
    }


    @Test
    public void test2() throws Exception {
        long l = System.currentTimeMillis();

        //ExecutorService executorService = Executors.newSingleThreadExecutor();
        //ExecutorService executorService = Executors.newFixedThreadPool(2);
        ExecutorService executorService = MoreExecutors.listeningDecorator(new ThreadPoolExecutor(2, 5, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(1), new ThreadFactoryBuilder()
                .setDaemon(true).setNameFormat("test-%d").build()));
        MoreExecutors.addDelayedShutdownHook(executorService, 10, TimeUnit.SECONDS);

        Future<String> one = executorService.submit(new MyCall("11"));
        Future<String> two = executorService.submit(new MyCall("222"));
        //System.out.println(one.get() + "::" + two.get());
        System.out.println(System.currentTimeMillis() - l);
        one.cancel(true);//如果任务正在执行,将会被中断
        Thread.currentThread().sleep(500);
        two.cancel(false);
        System.out.println(one.isCancelled() + "|||" + two.isCancelled());
    }

    @AllArgsConstructor
    class MyCall implements Callable<String> {
        private String context;

        @Override
        public String call() {
            while (!Thread.currentThread().isInterrupted()) {
                context += "a";
                if (context.length() > 10000) {
                    break;
                }
            }
            System.out.println("----" + Thread.currentThread().getName());
            return context;
        }
    }


}
