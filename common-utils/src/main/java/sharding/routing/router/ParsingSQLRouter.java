package sharding.routing.router;

import sharding.constant.DatabaseType;
import sharding.jdbc.core.ShardingContext;
import sharding.parsing.SQLParsingEngine;
import sharding.parsing.parser.sql.SQLStatement;
import sharding.parsing.parser.sql.dql.select.SelectStatement;
import sharding.routing.SQLExecutionUnit;
import sharding.routing.SQLRouteResult;
import sharding.routing.type.RoutingEngine;
import sharding.routing.type.RoutingResult;
import sharding.routing.type.TableUnit;
import sharding.routing.type.all.DatabaseAllRoutingEngine;
import sharding.rule.ShardingRule;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by zhaoheng on 18/10/18.
 */
public final class ParsingSQLRouter implements SQLRouter {

    //数据库和表分片规则配置
    private final ShardingRule shardingRule;

    private final DatabaseType databaseType;

    private final boolean showSQL;

    private final List<Number> generatedKeys;

    public ParsingSQLRouter(final ShardingContext shardingContext) {
        shardingRule = shardingContext.getShardingRule();
        databaseType = shardingContext.getDatabaseType();
        showSQL = shardingContext.isShowSQL();
        generatedKeys = new LinkedList<>();
    }

    @Override
    public SQLStatement parse(String logicSQL, int parametersSize) {
        SQLParsingEngine parsingEngine = new SQLParsingEngine(databaseType, logicSQL, shardingRule);
        SQLStatement result = parsingEngine.parse();
        /*if (result instanceof InsertStatement) {
            ((InsertStatement) result).appendGenerateKeyToken(shardingRule, parametersSize);
        }*/
        return result;
    }

    @Override
    public SQLRouteResult route(String logicSQL, List<Object> parameters, SQLStatement sqlStatement) {
        SQLRouteResult result = new SQLRouteResult(sqlStatement);
      /*  if (sqlStatement instanceof InsertStatement && null != ((InsertStatement) sqlStatement).getGeneratedKey()) {
            processGeneratedKey(parameters, (InsertStatement) sqlStatement, result);
        }*/
      /*  RoutingResult routingResult = route(parameters, sqlStatement);
        SQLRewriteEngine rewriteEngine = new SQLRewriteEngine(shardingRule, logicSQL, sqlStatement);
        boolean isSingleRouting = routingResult.isSingleRouting();
        if (sqlStatement instanceof SelectStatement && null != ((SelectStatement) sqlStatement).getLimit()) {
            processLimit(parameters, (SelectStatement) sqlStatement, isSingleRouting);
        }
        SQLBuilder sqlBuilder = rewriteEngine.rewrite(!isSingleRouting);
        if (routingResult instanceof CartesianRoutingResult) {
            for (CartesianDataSource cartesianDataSource : ((CartesianRoutingResult) routingResult).getRoutingDataSources()) {
                for (CartesianTableReference cartesianTableReference : cartesianDataSource.getRoutingTableReferences()) {
                    result.getExecutionUnits().add(new SQLExecutionUnit(cartesianDataSource.getDataSource(), rewriteEngine.generateSQL(cartesianTableReference, sqlBuilder)));
                }
            }
        } else {
            for (TableUnit each : routingResult.getTableUnits().getTableUnits()) {
                result.getExecutionUnits().add(new SQLExecutionUnit(each.getDataSourceName(), rewriteEngine.generateSQL(each, sqlBuilder)));
            }
        }
        if (showSQL) {
            SQLLogger.logSQL(logicSQL, sqlStatement, result.getExecutionUnits(), parameters);
        }*/
        return result;
    }

    private RoutingResult route(final List<Object> parameters, final SQLStatement sqlStatement) {
        Collection<String> tableNames = sqlStatement.getTables().getTableNames();
        RoutingEngine routingEngine = null;
      /*  if (tableNames.isEmpty()) {
            routingEngine = new DatabaseAllRoutingEngine(shardingRule.getDataSourceMap());
        } else if (1 == tableNames.size() || shardingRule.isAllBindingTables(tableNames) || shardingRule.isAllInDefaultDataSource(tableNames)) {
            routingEngine = new SimpleRoutingEngine(shardingRule, parameters, tableNames.iterator().next(), sqlStatement);
        } else {
            // TODO config for cartesian set
            routingEngine = new ComplexRoutingEngine(shardingRule, parameters, tableNames, sqlStatement);
        }*/
        return routingEngine.route();
    }
}
