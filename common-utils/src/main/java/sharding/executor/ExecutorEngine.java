package sharding.executor;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import sharding.exception.ShardingJdbcException;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhaoheng on 18/10/17.
 */
public class ExecutorEngine implements AutoCloseable {

    private final ListeningExecutorService executorService;


    public ExecutorEngine(final int executorSize) {
        executorService = MoreExecutors.listeningDecorator(new ThreadPoolExecutor(
                executorSize, executorSize, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), new ThreadFactoryBuilder().setDaemon(true).setNameFormat("ShardingJDBC-%d").build()));

        MoreExecutors.addDelayedShutdownHook(executorService, 60, TimeUnit.SECONDS);
    }


    @Override
    public void close() throws Exception {
        executorService.shutdownNow();
        try {
            executorService.awaitTermination(5, TimeUnit.SECONDS);
        } catch (final InterruptedException ignored) {
        }
        if (!executorService.isTerminated()) {
            throw new ShardingJdbcException("ExecutorEngine can not been terminated");
        }
    }
}
