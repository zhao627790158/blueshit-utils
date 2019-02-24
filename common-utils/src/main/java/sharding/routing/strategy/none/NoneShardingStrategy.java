package sharding.routing.strategy.none;

import lombok.Getter;
import sharding.api.algorithm.sharding.ShardingValue;
import sharding.routing.strategy.ShardingStrategy;

import java.util.Collection;
import java.util.Collections;

@Getter
public final class NoneShardingStrategy implements ShardingStrategy {

    private final Collection<String> shardingColumns = Collections.emptyList();

    @Override
    public Collection<String> doSharding(final Collection<String> availableTargetNames, final Collection<ShardingValue> shardingValues) {
        return availableTargetNames;
    }
}