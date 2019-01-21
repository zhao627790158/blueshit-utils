package sharding.routing.strategy;

import sharding.api.algorithm.sharding.ShardingValue;

import java.util.Collection;

/**
 * Created by zhaoheng on 18/10/17.
 * 分片策略
 */
public interface ShardingStrategy {


    /**
     * 获取分片列
     *
     * @return 分片列
     */
    Collection<String> getShardingColumns();

    /**
     * 执行分片
     * @param availableTargetNames
     * @param shardingValues 分片值
     * @return
     */
    Collection<String> doSharding(Collection<String> availableTargetNames, Collection<ShardingValue> shardingValues);

}
