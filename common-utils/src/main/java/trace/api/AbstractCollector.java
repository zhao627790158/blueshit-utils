package trace.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zhaoheng on 18/7/20.
 */
public abstract class AbstractCollector implements ICollector {

    private final static Logger logger = LoggerFactory.getLogger(AbstractCollector.class);


    protected final String name;
    protected int size;
    protected int uploadSize;
    protected int interval;
    protected final AtomicInteger failCount = new AtomicInteger(0);
    public final static int MAX_QUEUE_SIZE = 1024 * 8;
    public final static int DEFAULT_INTERVAL = 1024;
    public final static int UPLOAD_SIZE = 1024;
    protected BlockingQueue<Span> queue;
    protected ArrayList<Span> retryList;
    public volatile boolean isActive = true;
    protected Thread t;

    public AbstractCollector(String name) {
        this.name = name;
        this.interval = DEFAULT_INTERVAL;
        this.size = MAX_QUEUE_SIZE;
        this.uploadSize = UPLOAD_SIZE;
        addShutdownHook();
    }

    protected void start() {
        queue = new ArrayBlockingQueue<Span>(size);
        retryList = new ArrayList<Span>(uploadSize);
        t = new CollectorThreadFactory(this.name).newThread(new Dispatcher());
        t.start();
        logger.debug("Collector {} start size {} interval {}", name, size, interval);
    }

    @Override
    public void collect(final Span span) {
        logger.debug("Collect Span {}", span);
        if (t == null || !this.isActive || span == null) {
            return;
        }

        int cost = span.getCost();
        if (cost >= 1000) {
            //超过服务阈值强制上报
            span.setForce(true);
        }

        // 非DEBUG 标识的数据，只会采集指定的类型
        if (!span.isDebug()) {
            return;
        }
        // server端直接上报到队列, client端如果有parent节点且parent节点子节点个数小于256则添加到父节点后面, 否则也直接上报到队列
        if (span.getType() == Span.SIDE.SERVER) {
            offer(span);
        } else {
            Span parent = span.getParent();
            // 无无节点或者有父节点，但是父节点的子节点个数小于256
            if (parent != null && !parent.isVirtual() && (parent.getChildren() == null || parent.getChildren().size() < 256)) {
                parent.addChildren(span);
            } else {
                offer(span);
            }
        }
    }

    private void offer(final Span span) {
        if (!queue.offer(span)) {
            failCount.incrementAndGet();
        }

    }


    /**
     * 上报函数, 批量上报接口, 由Dispatcher线程调用
     *
     * @param ts 上报数据
     * @return 是否上报成功
     */
    protected abstract boolean upload(List<Span> ts);

    protected void addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                isActive = false;
                List<Span> spans = new ArrayList<Span>(size);
                queue.drainTo(spans, uploadSize);
                logger.debug("Collector " + name + " before shutdown upload " + spans.size() +
                        " retry " + retryList.size() +
                        " fail " + failCount.getAndSet(0));
                if (!retryList.isEmpty()) {
                    spans.addAll(retryList);
                    retryList.clear();
                }
                if (!spans.isEmpty()) {
                    upload(spans);
                }
            }
        });
    }

    private static class CollectorThreadFactory implements ThreadFactory {

        private final String name;

        public CollectorThreadFactory(String name) {
            this.name = name;
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setName(name);
            thread.setDaemon(true);
            return thread;
        }
    }

    private class Dispatcher implements Runnable {

        /**
         * total : 上报的数据项列表
         * ts : 从队列里拿出来的需要上报的数据项
         * retryList : 需要重试的数据项列表
         */
        @Override
        public void run() {
            while (isActive) {
                try {
                    List<Span> total = new ArrayList<Span>(uploadSize * 2);
                    List<Span> ts = new ArrayList<Span>(uploadSize);
                    queue.drainTo(ts, uploadSize);
                    logger.debug("Collector " + name + " dispatcher loop interval " + interval +
                            " upload " + ts.size() +
                            " retry " + retryList.size() +
                            " fail " + failCount.getAndSet(0));
                    if (!retryList.isEmpty()) {
                        total.addAll(retryList);
                        retryList.clear();
                    }
                    total.addAll(ts);
                    if (!upload(total)) {
                        retryList.addAll(ts);
                    }
                    Thread.sleep(interval);
                } catch (Exception e) {
                    logger.warn("TraceCollector Dispatcher exception", e);
                }
            }
        }
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public boolean isActive() {
        return isActive;
    }

    @Override
    public void setActive(boolean active) {
        isActive = active;
    }

    public int getUploadSize() {
        return uploadSize;
    }

    public void setUploadSize(int uploadSize) {
        this.uploadSize = uploadSize;
    }
}
