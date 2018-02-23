package cn.blueshit.cn.test.current;

import com.google.common.util.concurrent.RateLimiter;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * Created by zhaoheng on 17/5/31.
 */
public class RateLimiterTest {

    @Test
    public void test() {

        RateLimiter limiter = RateLimiter.create(5);
        System.out.println(limiter.acquire());
        System.out.println(limiter.acquire());
        System.out.println(limiter.acquire());
        System.out.println(limiter.acquire());
        System.out.println(limiter.acquire());
        System.out.println(limiter.acquire());

    }

    @Test
    public void test1() throws InterruptedException {

        RateLimiter limiter = RateLimiter.create(5, 1000, TimeUnit.MILLISECONDS);
        for (int i = 1; i < 5; i++) {
            System.out.println(limiter.acquire());
        }
        Thread.sleep(1000L);
        for (int i = 1; i < 5; i++) {
            System.out.println(limiter.acquire());
        }
    }

}
