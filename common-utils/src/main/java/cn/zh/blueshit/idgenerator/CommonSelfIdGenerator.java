package cn.zh.blueshit.idgenerator;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import java.util.Calendar;

/**
 * 优点:分布式 弱有序,本地计算不依靠数据库/缓存等. 可用性高
 * 缺点:
 * 强依赖机器时钟，如果机器上时钟回拨，会导致发号重复或者服务会处于不可用状态。
 * !!!!可以直接关闭NTP同步。要么在时钟回拨的时候直接不提供服务直接返回ERROR_CODE，等时钟追上即可!!!!!
 * 在2017年闰秒那一次出现过部分机器回拨
 * <p>
 * <p>
 * <p>
 * workid为系统变量用于区分不同的机器,1台机器最好单实例 现在线上也是这么做的
 * 此id生成可以理解为分布式 大约有序的,可以根据大小来排查
 * 符号位+时间差+workid+毫秒能自增 ===64bits mysql数据库使用bigint
 * <p>
 * 长度为64bit,从高位到低位依次为
 * 1bit   符号位
 * 41bits 时间偏移量从2016年11月1日零点到现在的毫秒数
 * 10bits 工作进程Id
 * 12bits 同一个毫秒内的自增量
 * 例如:
 * 补0 0..1101101001001101001010111101110101 0000000000  000000010001         workid=0
 * 补0 0..1101101001011000001011000110011111 0000000101   000000000000        workid=5
 * 符号 ////////////////时间偏移                   workid      同一毫秒内的自增量
 * <p>
 * <p>
 * Created by zhaoheng on 17/4/19.
 */
public class CommonSelfIdGenerator implements IdGenerator {

    public static final long SJDBC_EPOCH;

    private static final long SEQUENCE_BITS = 12L;

    private static final long INSTANCE_BITS = 4L;

    private static final long WORKER_ID_BITS = 10L;

    public static final long SEQUENCE_MASK = (1 << SEQUENCE_BITS) - 1;

    private static final long WORKER_ID_LEFT_SHIFT_BITS = SEQUENCE_BITS;

    private static final long TIMESTAMP_LEFT_SHIFT_BITS = WORKER_ID_LEFT_SHIFT_BITS + WORKER_ID_BITS;

    private static final long WORKER_ID_MAX_VALUE = 1L << WORKER_ID_BITS;

    private static AbstractClock clock = AbstractClock.systemClock();

    private static long workerId;

    private static long instanceId;

    private long sequence;

    private long lastTime;

    static {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2016, Calendar.NOVEMBER, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        SJDBC_EPOCH = calendar.getTimeInMillis();
        initWorkerId();
    }

    static void initWorkerId() {
        //系统环境变量
        String workerId = System.getenv("ID_GENERATOR_WORKER_ID");
        if (Strings.isNullOrEmpty(workerId)) {
            return;
        }
        setWorkerId(Long.valueOf(workerId));
    }


    @Override
    public synchronized Number generateId() throws InterruptedException {
        long time = clock.millis();
        //发生了回拨，此刻时间小于上次发号时间
        if (time < lastTime) {
            long offset = lastTime - time;
            if (offset <= 5) {
                try {
                    wait(offset << 1);
                    time = clock.millis();
                    if (time < lastTime) {
                        //还是小于，抛异常
                        throw new IllegalStateException("系统时间发生了回拨" + time);
                    }
                } catch (Exception e) {
                    throw e;
                }
            } else {
                throw new IllegalStateException("系统时间发生了回拨" + time);
            }
        }
        if (lastTime == time) {
            //每毫秒只支持生成4095个id,如果超过4095,将会等到下一毫秒生成
            if (0L == (sequence = ++sequence & SEQUENCE_MASK)) {
                time = waitUntilNextTime(time);
                throw new IllegalStateException("系统时间发生了回拨" + time);
            }
        } else {
            sequence = 0;
        }
        lastTime = time;
        //当前时间和2016年11月1日的时间差左移22位,这样后22bit就可以填充 workid==sequence了
        return ((time - SJDBC_EPOCH) << TIMESTAMP_LEFT_SHIFT_BITS) | (workerId << WORKER_ID_LEFT_SHIFT_BITS) | sequence;
    }

    public static void setWorkerId(long workerId) {
        //最大支持1024个workid,因为workid 就12bits - -
        Preconditions.checkArgument(workerId >= 0L && workerId < WORKER_ID_MAX_VALUE);
        CommonSelfIdGenerator.workerId = workerId;
    }

    public static void setClock(AbstractClock clock) {
        CommonSelfIdGenerator.clock = clock;
    }

    private long waitUntilNextTime(final long lastTime) {
        long time = clock.millis();
        while (time <= lastTime) {
            time = clock.millis();
        }
        return time;
    }
}
