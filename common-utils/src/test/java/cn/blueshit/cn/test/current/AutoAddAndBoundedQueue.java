package cn.blueshit.cn.test.current;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by zhaoheng on 17/10/12.
 */
public class AutoAddAndBoundedQueue implements Runnable {

    private final BlockingDeque<Long> supplement = new LinkedBlockingDeque<Long>();

    @Override
    public void run() {
        if (supplement.size() < 10000) {
            supplement.pollFirst();
        }
    }
}
