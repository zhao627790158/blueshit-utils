package cn.blueshit.cn.test.quartz;

import org.junit.Test;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.lang.instrument.Instrumentation;

/**
 * Created by zhaoheng on 18/12/31.
 */
public class Demo {


    @Test
    public void testStart() throws Exception {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        scheduler.start();
    }

    class TestJob implements Job {
        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            System.out.println(getClass());
        }
    }

    public static void main(String[] arges) {

    }

    public static void agentmain(String agentArgs, Instrumentation instrumentation) {

        System.out.println("test");
    }


}
