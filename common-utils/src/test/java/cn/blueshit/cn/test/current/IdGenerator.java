package cn.blueshit.cn.test.current;

import cn.zh.blueshit.current.ExecutorServiceObject;
import cn.zh.blueshit.idgenerator.AbstractClock;
import cn.zh.blueshit.idgenerator.CommonSelfIdGenerator;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.math3.util.FastMath;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by zhaoheng on 17/4/21.
 */
public class IdGenerator {


    private static final ExecutorServiceObject executorServiceObject = new ExecutorServiceObject("IdGeneratorTest", 20, 500);


    @Before
    public void init() {
        CommonSelfIdGenerator.setClock(AbstractClock.systemClock());
    }

    @Test
    public void getNo() throws Exception {
        int threadNumber = Runtime.getRuntime().availableProcessors() << 1;
        ExecutorService executorService = executorServiceObject.createExecutorService();
        final int taskNumber = threadNumber << 15;
        final CommonSelfIdGenerator idGenerator = new CommonSelfIdGenerator();
        idGenerator.setWorkerId(1);//每台机器部署应用前需要设置
        Set<Long> list = new HashSet<>();
        Stopwatch started = Stopwatch.createStarted();
        for (int i = 0; i < taskNumber; i++) {
            list.add(executorService.submit(new Callable<Long>() {
                @Override
                public Long call() throws Exception {
                    return (Long) idGenerator.generateId();
                }
            }).get());
        }
        assertThat(list.size(), is(taskNumber));
        long nanos = started.elapsed(TimeUnit.MILLISECONDS);

        System.out.println(list.size() + "---" + nanos);
        //524288---5377
        //524288---5454

        /*List list1 = new ArrayList<>(list);
        list1.sort(new Comparator<Long>() {
            @Override
            public int compare(Long o1, Long o2) {
                return FastMath.toIntExact(o2.longValue() - o1.longValue());
            }
        });
        System.out.println(list1);*/
    }
}
