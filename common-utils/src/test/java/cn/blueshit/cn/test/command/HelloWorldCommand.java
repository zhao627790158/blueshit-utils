package cn.blueshit.cn.test.command;

import com.netflix.hystrix.*;
import lombok.extern.slf4j.Slf4j;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by zhaoheng on 16/8/17.
 */
@Slf4j
public class HelloWorldCommand extends HystrixCommand<String> {

    /*
    * HystxixCommand支持如下的配置：
        GroupKey：该命令属于哪一个组，可以帮助我们更好的组织命令。
        CommandKey：该命令的名称
        ThreadPoolKey：该命令所属线程池的名称，同样配置的命令会共享同一线程池，若不配置，会默认使用GroupKey作为线程池名称。
        CommandProperties：该命令的一些设置，包括断路器的配置，隔离策略，降级设置，以及一些监控指标等。
        ThreadPoolProerties：关于线程池的配置，包括线程池大小，排队队列的大小等。
    * */

    private final String group;

    private final String name;

    public HelloWorldCommand(String groupName, String commandKey, int timeout) {
        super(HystrixCommand.Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey(groupName))
                .andCommandKey(HystrixCommandKey.Factory.asKey(commandKey))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withExecutionTimeoutInMilliseconds(timeout))
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                        .withCoreSize(10)));
        this.name = commandKey;
        this.group = groupName;
    }

    @Override
    protected String run() throws Exception {

        Thread.sleep(500);
        int i = 1 / 0;
        return "Hello " + name + " thread:" + Thread.currentThread().getName() + " group"
                + this.getCommandGroup().name() + " key" + this.getCommandKey().name();
    }

    @Override
    protected String getFallback() {
        return "fallback";
    }

    //调用实例
    public static void main(String[] args) throws Exception {
        //每个Command对象只能调用一次,不可以重复调用,
        //重复调用对应异常信息:This instance can only be executed once. Please instantiate a new instance.
        HelloWorldCommand helloWorldCommand = null;
        try {
            helloWorldCommand=new HelloWorldCommand("group", "commandKey", 100);
        } catch (Throwable throwable) {
            log.error("11111111111");
        }

        //观察执行状态
        Observable<String> observe = helloWorldCommand.observe();
        Subscription subscribe = observe.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                log.info("Command called. Result is:{}", s);
            }
        });
        Thread.sleep(1000);
        //String result = helloWorldCommand.execute();
       /* //使用execute()同步调用代码,效果等同于:helloWorldCommand.queue().get();
        String result = helloWorldCommand.execute();
        System.out.println("result=" + result);
        */

        HelloWorldCommand helloWorldCommand1 = new HelloWorldCommand("group", "commandKey", 100);
        Observable<String> observe1 = helloWorldCommand1.observe();
        observe1.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                log.info("Command Completed");
            }

            @Override
            public void onError(Throwable e) {
                log.info("Command failled", e);
            }

            @Override
            public void onNext(String s) {
                log.info("Command finished,result is {}", s);
            }
        });
        Thread.sleep(1000);

        //异步调用,可自由控制获取结果时机,
        // Future<String> future = helloWorldCommand.queue();
        //get操作不能超过command定义的超时时间,默认:1秒
        //result = future.get(100, TimeUnit.MILLISECONDS);
        // System.out.println("result=" + result);
        System.out.println("mainThread=" + Thread.currentThread().getName());
    }

}
