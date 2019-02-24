package sharding.routing.router;

import sharding.parsing.parser.sql.SQLStatement;
import sharding.routing.SQLRouteResult;

import java.util.List;

public interface SQLRouter {

    /**
     * Parse SQL.
     *
     * @param logicSQL       logic SQL
     * @param parametersSize parameters size
     * @return parse result
     */
    SQLStatement parse(String logicSQL, int parametersSize);

    /**
     * Route SQL.
     *
     * @param logicSQL     logic SQL
     * @param sqlStatement SQL statement
     * @param parameters   parameters
     * @return parse result
     */
    SQLRouteResult route(String logicSQL, List<Object> parameters, SQLStatement sqlStatement);
}