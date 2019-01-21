package sharding.jdbc.core;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import sharding.constant.DatabaseType;
import sharding.executor.ExecutorEngine;
import sharding.rule.ShardingRule;

@RequiredArgsConstructor
@Getter
public final class ShardingContext {

    private final ShardingRule shardingRule;

    private final DatabaseType databaseType;

    private final ExecutorEngine executorEngine;

    private final boolean showSQL;
}