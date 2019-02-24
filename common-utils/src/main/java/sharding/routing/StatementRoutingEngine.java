package sharding.routing;

import sharding.jdbc.core.ShardingContext;
import sharding.parsing.parser.sql.SQLStatement;
import sharding.routing.router.SQLRouter;
import sharding.routing.router.SQLRouterFactory;

import java.util.Collections;

public final class StatementRoutingEngine {

    private final SQLRouter sqlRouter;

    public StatementRoutingEngine(final ShardingContext shardingContext) {
        sqlRouter = SQLRouterFactory.createSQLRouter(shardingContext);
    }

    /**
     * SQL route.
     *
     * @param logicSQL logic SQL
     * @return route result
     */
    public SQLRouteResult route(final String logicSQL) {
        SQLStatement sqlStatement = sqlRouter.parse(logicSQL, 0);
        return sqlRouter.route(logicSQL, Collections.emptyList(), sqlStatement);
    }
}
