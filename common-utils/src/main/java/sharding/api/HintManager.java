package sharding.api;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sharding.api.algorithm.sharding.ShardingValue;
import sharding.hint.HintManagerHolder;
import sharding.hint.ShardingKey;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhaoheng on 18/10/17.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class HintManager implements AutoCloseable {

    private final Map<ShardingKey, ShardingValue> databaseShardingValues = new HashMap<>();

    private final Map<ShardingKey, ShardingValue> tableShardingValues = new HashMap<>();

    @Getter
    private boolean shardingHint;

    @Getter
    private boolean masterRouteOnly;

    @Getter
    private boolean databaseShardingOnly;


    @Override
    public void close() throws Exception {
        HintManagerHolder.clear();
    }
}
