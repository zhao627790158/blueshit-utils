package sharding.executor;

import sharding.routing.SQLExecutionUnit;

import java.sql.Statement;

public interface BaseStatementUnit {

    /**
     * Get SQL execute unit.
     *
     * @return SQL execute unit
     */
    SQLExecutionUnit getSqlExecutionUnit();

    /**
     * Get statement.
     *
     * @return statement
     */
    Statement getStatement();
}
