package sharding.api.config.strategy;

import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import sharding.routing.strategy.ShardingStrategy;
import sharding.routing.strategy.inline.InlineShardingStrategy;

/**
 * Created by zhaoheng on 18/10/17.
 */
@RequiredArgsConstructor
@Getter
public class InlineShardingStrategyConfiguration implements ShardingStrategyConfiguration {

    private final String shardingColumn;

    private final String algorithmInlineExpression;

    @Override
    public ShardingStrategy build() {
        Preconditions.checkNotNull(shardingColumn, "Sharding column cannot be null.");
        Preconditions.checkNotNull(algorithmInlineExpression, "Algorithm inline expression cannot be null.");
        return new InlineShardingStrategy(shardingColumn, algorithmInlineExpression);
    }
}
