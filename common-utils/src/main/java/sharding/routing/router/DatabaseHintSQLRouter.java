package sharding.routing.router;

import sharding.jdbc.core.ShardingContext;
import sharding.parsing.SQLJudgeEngine;
import sharding.parsing.parser.sql.SQLStatement;
import sharding.routing.SQLRouteResult;
import sharding.rule.ShardingRule;

import java.util.List;

/**
 * Created by zhaoheng on 18/10/18.
 */
public final class DatabaseHintSQLRouter implements SQLRouter {


    private final ShardingRule shardingRule;
    private final boolean showSQL;

    public DatabaseHintSQLRouter(final ShardingContext shardingContext) {
        shardingRule = shardingContext.getShardingRule();
        showSQL = shardingContext.isShowSQL();
    }


    @Override
    public SQLStatement parse(String logicSQL, int parametersSize) {
        return new SQLJudgeEngine(logicSQL).judge();
    }

    @Override
    public SQLRouteResult route(String logicSQL, List<Object> parameters, SQLStatement sqlStatement) {
       /* SQLRouteResult result = new SQLRouteResult(sqlStatement);
        RoutingResult routingResult = new DatabaseHintRoutingEngine(shardingRule.getDataSourceMap(), (HintShardingStrategy) shardingRule.getDefaultDatabaseShardingStrategy()).route();
*/
        return null;
    }
}
