package cn.blueshit.cn.test.job;

import com.alibaba.fastjson.JSON;
import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.util.ReflectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by zhaoheng on 16/12/27.
 */
public class TestJob implements Job {

    private Job myJob;

    public Job getMyJob() {
        return myJob;
    }

    public void setMyJob(Job myJob) {
        this.myJob = myJob;
    }

    public void test() {
        TestJob testJob = new TestJob();
        System.out.println(testJob);
        //设置的为调用者
        testJob.setMyJob(this);
        System.out.println(testJob.getMyJob());
    }

    @Override
    public void execute(final JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("Hello Quartz !" + DateTime.now().toString("HH:mm:ss"));
        //test this
        TestJob testJob = new TestJob();
        System.out.println("调用者" + testJob);
        testJob.test();
        //// 获取实体类的所有属性，返回Field数组 getDeclaredFields包含 public private protect但是不包含父类的属性
        Field[] fields = jobExecutionContext.getClass().getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
        }
        //输出p1的所有属性
        System.out.println("=============About p1===============");
        ReflectionUtils.doWithFields(jobExecutionContext.getClass(), new ReflectionUtils.FieldCallback() {
            @Override
            public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
                field.setAccessible(true);
                String fieldName = field.toString().substring(field.toString().lastIndexOf(".") + 1);         //取出属性名称
                System.out.println(fieldName + " --> " + field.get(jobExecutionContext));
            }
        });
       /* System.out.println("=============About p1===============");
        for (Field f : fields) {
            System.out.println("f--->" + f);
            String field = f.toString().substring(f.toString().lastIndexOf(".") + 1);         //取出属性名称
            try {
                System.out.println("p1." + field + " --> " + f.get(jobExecutionContext));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }*/
    }

    public static void main(String[] args) {


        //通过schedulerFactory获取一个调度器
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = null;

        try {
            // 通过schedulerFactory获取一个调度器
            scheduler = schedulerFactory.getScheduler();
            System.out.println("-------" + scheduler.isInStandbyMode());
            // 创建jobDetail实例，绑定Job实现类
            // 指明job的名称，所在组的名称，以及绑定job类
            JobDetail job = JobBuilder.newJob(TestJob.class).withIdentity("JobName", "JobGroupName").build();
            // 定义调度触发规则

            // SimpleTrigger
//      Trigger trigger=TriggerBuilder.newTrigger().withIdentity("SimpleTrigger", "SimpleTriggerGroup")
//                    .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(3).withRepeatCount(6))
//                    .startNow().build();


            //corn表达式  每五秒执行一次
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("CronTrigger1", "CronTriggerGroup")
                    .withSchedule(CronScheduleBuilder.cronSchedule("*/5 * * * * ?"))
                    .startNow().build();
            //把作业和触发器注册到任务调度中
            scheduler.scheduleJob(job, trigger);
            scheduler.start();
            System.out.println("-------" + scheduler.isInStandbyMode());
            System.out.println("-------" + scheduler.getSchedulerInstanceId() + ":" + scheduler.getSchedulerName() + ":"
                    + JSON.toJSONString(scheduler.getCurrentlyExecutingJobs()));
            Thread.sleep(10000);
            System.out.println("-------" + scheduler.getSchedulerInstanceId() + ":" + scheduler.getSchedulerName() + ":"
                    + JSON.toJSONString(scheduler.getCurrentlyExecutingJobs()));
            // 停止调度
            scheduler.shutdown();

        } catch (SchedulerException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
