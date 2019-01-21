package sharding.routing.strategy.inline;

import com.google.common.base.Joiner;
import groovy.lang.Closure;
import groovy.lang.GroovyShell;
import sharding.api.algorithm.sharding.ShardingValue;
import sharding.routing.strategy.ShardingStrategy;

import java.util.Collection;
import java.util.TreeSet;

/**
 * Created by zhaoheng on 18/10/17.
 * 内嵌表达式分片策略
 * new InlineShardingStrategyConfiguration("user_id", "ds_${user_id % 2}")
 * new InlineShardingStrategy(shardingColumn, algorithmInlineExpression)
 */
public class InlineShardingStrategy implements ShardingStrategy {

    private final String shardingColumn;

    //groovy闭包
    private final Closure<?> closure;

    public InlineShardingStrategy(final String shardingColumn, final String inlineExpression) {
        this.shardingColumn = shardingColumn;
        //"{it -> "ds_${user_id % 2}"}"
        closure = (Closure) new GroovyShell().evaluate(Joiner.on("").join("{it -> \"", inlineExpression.trim(), "\"}"));
    }


    @Override
    public Collection<String> getShardingColumns() {
        Collection<String> result = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        result.add(shardingColumn);
        return result;
    }

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, Collection<ShardingValue> shardingValues) {
        //todo

        return null;
    }
}
