package cn.blueshit.cn.test.current;

import com.google.common.base.Stopwatch;

import java.io.Serializable;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zhaoheng on 16/9/24.
 */
public class TestBlockDeque {

    private static final ExecutorService executor = Executors.newCachedThreadPool();
    private final static BlockingDeque<AtomicInteger> deque = new LinkedBlockingDeque<AtomicInteger>(1000);


    public static void main(String[] args) throws Exception {

        Stopwatch started = Stopwatch.createStarted();

        started.stop();
        Future submitOffer = executor.submit(new CallBackOffer(started, deque));

        Future submitPoll = executor.submit(new CallBackPoll(started, deque));
        printf(submitOffer.get());
        printf(started.elapsed(TimeUnit.MICROSECONDS));
        printf(submitPoll.get());
        printf(started.elapsed(TimeUnit.MICROSECONDS));

    }


    private static void printf(Object o) {
        System.out.println(o.toString());
    }


    private void testQueue() {
        //双端队列
        BlockingDeque deque = new LinkedBlockingDeque();
        //队列满的话会扔异常
        deque.addFirst(new Object());
        //队列满的话 直接返回false
        deque.offerFirst(new Object());
        try {
            //队列满 会堵塞
            deque.putFirst(new Object());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}

class CallBackOffer implements Callable {
    private Stopwatch stopwatch;

    private BlockingDeque deque;

    public CallBackOffer(Stopwatch stopwatch, BlockingDeque deque) {
        this.stopwatch = stopwatch;
        this.deque = deque;
    }

    @Override
    public Object call() throws Exception {
        for (int i = 0; i < 1000; i++) {
            deque.offerLast(i);
        }
        return "test call";
    }
}

class CallBackPoll implements Callable {
    private Stopwatch stopwatch;

    private BlockingDeque deque;

    public CallBackPoll(Stopwatch stopwatch, BlockingDeque deque) {
        this.stopwatch = stopwatch;
        this.deque = deque;
    }

    @Override
    public Object call() throws Exception {

        for (int i = 0; i < 1000; i++) {
            Object o = deque.pollFirst();
            if (null == o) {
                System.out.println("返回null");
            }
        }
        return "test call";
    }
}