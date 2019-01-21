package sharding.api.config.strategy;

import sharding.routing.strategy.ShardingStrategy;

/**
 * Created by zhaoheng on 18/10/17.
 * 分片策略配置
 */
public interface ShardingStrategyConfiguration {


    /**
     * 构建分片策略
     *
     * @return sharding strategy
     */
    ShardingStrategy build();


}
