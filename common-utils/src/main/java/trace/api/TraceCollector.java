package trace.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zhaoheng on 18/7/20.
 */
public class TraceCollector extends AbstractCollector {
    private final static Logger logger = LoggerFactory.getLogger(AbstractCollector.class);

    public final static int MAX_INTERVAL = 8 * 1024; // 最大上报间隔
    public final static int MIN_INTERVAL = 2; // 最小上报间隔
    private AtomicInteger requestCount = new AtomicInteger(0); // server端trace头请求的数目
    private long lastCycleTime; // 上一个周期的时间,主要用于reset trace头节点请求数目 requestCount

    public TraceCollector() {
        super(TraceCollector.class.getSimpleName());
        lastCycleTime = System.currentTimeMillis();
        setInterval(MIN_INTERVAL);
        start();
    }

    /**
     * 上报逻辑, 根据每次上报队列中取出的数据大小, 动态变更上报的周期
     *
     * @param spans 上传列表
     * @return 上报是否success
     */
    @Override
    protected boolean upload(List<Span> spans) {
        boolean status = true;
        if (!spans.isEmpty()) {
            //todo 具体的上传逻辑
        }
        int uploadSize = spans.size();
        if (uploadSize >= UPLOAD_SIZE && interval > MIN_INTERVAL) {
            interval = interval / 2;
        } else if (uploadSize < UPLOAD_SIZE && interval < MAX_INTERVAL) {
            interval = interval * 2;
        }
        long now = System.currentTimeMillis();
        if (now - lastCycleTime > MAX_INTERVAL) {
            lastCycleTime = now;
            requestCount.set(0);
        }
        return status;
    }

}
