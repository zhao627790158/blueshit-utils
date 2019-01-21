package sharding.routing.router;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import sharding.hint.HintManagerHolder;
import sharding.jdbc.core.ShardingContext;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SQLRouterFactory {

    /**
     * Create SQL router.
     *
     * @param shardingContext Sharding runtime context
     * @return SQL router instance
     */
    public static SQLRouter createSQLRouter(final ShardingContext shardingContext) {
        return HintManagerHolder.isDatabaseShardingOnly() ? new DatabaseHintSQLRouter(shardingContext) : new ParsingSQLRouter(shardingContext);
    }
}