package cn.zh.blueshit.current.callable;

import java.util.concurrent.Callable;

/**
 * Created by zhaoheng on 2016/6/3.
 */
public abstract class BaseCallable<T> implements Callable<T> {

    @Override
    public abstract T call() throws Exception;

}
