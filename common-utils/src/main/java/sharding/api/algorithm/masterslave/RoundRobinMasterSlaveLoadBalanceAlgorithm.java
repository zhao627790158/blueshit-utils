package sharding.api.algorithm.masterslave;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 循环负载均衡选择从库
 */

public final class RoundRobinMasterSlaveLoadBalanceAlgorithm implements MasterSlaveLoadBalanceAlgorithm {

    private static final ConcurrentHashMap<String, AtomicInteger> COUNT_MAP = new ConcurrentHashMap<>();

    @Override
    public String getDataSource(final String name, final String masterDataSourceName, final List<String> slaveDataSourceNames) {
        AtomicInteger count = COUNT_MAP.containsKey(name) ? COUNT_MAP.get(name) : new AtomicInteger(0);
        COUNT_MAP.putIfAbsent(name, count);
        count.compareAndSet(slaveDataSourceNames.size(), 0);
        return slaveDataSourceNames.get(count.getAndIncrement() % slaveDataSourceNames.size());
    }
}
