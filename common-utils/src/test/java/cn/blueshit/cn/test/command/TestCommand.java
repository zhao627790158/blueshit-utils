package cn.blueshit.cn.test.command;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.*;

/**
 * Created by zhaoheng on 16/8/18.
 */
@Slf4j
public class TestCommand {

    private static ThreadPoolExecutor threadPoolExecutor =
            new ThreadPoolExecutor(5, 10, 2, TimeUnit.SECONDS,
                    new ArrayBlockingQueue<Runnable>(2), new ThreadPoolExecutor.DiscardPolicy());

    private static volatile boolean flag = false;

    private CountDownLatch count = new CountDownLatch(1);

    @Test
    public void test() throws InterruptedException, ExecutionException {
       /* threadPoolExecutor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                count.countDown();
            }
        });*/
        HelloWorldCommand helloWorldCommand = new HelloWorldCommand("group", "key", 1000);
        try {
            Future<String> queue = helloWorldCommand.queue();
            /*while (count.getCount() != 0) {
                log.info("isSuccessfulExecution:" + helloWorldCommand.isSuccessfulExecution() + "-" + threadPoolExecutor.getActiveCount());
                log.info("isFailedExecution:" + helloWorldCommand.isFailedExecution());
                Thread.sleep(500);
            }*/
            log.error("ee" + queue.get() + "core count");
            log.error("ee" + queue.get() + "core count");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //getFailedExecutionException pass isFailedExecution
        /*if (null == helloWorldCommand.getExecutionException() || null == helloWorldCommand.getExecutionException().getMessage()) {
            log.info("fallback111:{}", null == helloWorldCommand.getExecutionException().getMessage());
        }*/

    }
}
