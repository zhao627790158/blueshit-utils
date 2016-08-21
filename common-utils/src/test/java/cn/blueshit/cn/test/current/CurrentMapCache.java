package cn.blueshit.cn.test.current;

import java.util.concurrent.*;

/**
 * Created by zhaoheng on 16/8/20.
 */
public class CurrentMapCache<A, V> {

    private final ConcurrentHashMap<A, Future<V>> cacheMap = new ConcurrentHashMap<A, Future<V>>();

    public V get(final A arg) throws InterruptedException {
        while (true) {
            Future<V> f = cacheMap.get(arg);
            if (f == null) {
                Callable<V> callable = new Callable<V>() {
                    @Override
                    public V call() throws Exception {
                        //do something
                        return null;
                    }
                };
                FutureTask<V> ft = new FutureTask<V>(callable);
                //若没有则添加, 添加成功返回null
                f = cacheMap.putIfAbsent(arg, ft);
                if (f == null) {
                    f = ft;
                    ft.run();
                }
            }
            try {
                //可以加超时时间
                return f.get(2, TimeUnit.SECONDS);
            } catch (CancellationException e) {
                cacheMap.remove(arg, f);
            } catch (ExecutionException e) {
                //throw execption(e.getCause)
            } catch (TimeoutException e) {
                //f.cancel(true);
                cacheMap.remove(arg, f);
            }

        }
    }


}
