package sharding.jdbc.core.statement;

import lombok.AccessLevel;
import lombok.Getter;
import sharding.executor.statement.StatementExecutor;
import sharding.jdbc.adapter.AbstractStatementAdapter;
import sharding.jdbc.core.connection.ShardingConnection;
import sharding.parsing.parser.sql.dql.select.SelectStatement;
import sharding.routing.SQLRouteResult;
import sharding.routing.StatementRoutingEngine;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by zhaoheng on 18/10/17.
 */
@Getter
public class ShardingStatement extends AbstractStatementAdapter {

    private final ShardingConnection connection;

    private final int resultSetType;

    private final int resultSetConcurrency;

    private final int resultSetHoldability;

    private final Collection<Statement> routedStatements = new LinkedList<>();

    @Getter(AccessLevel.NONE)
    private boolean returnGeneratedKeys;

    @Getter(AccessLevel.NONE)
    private SQLRouteResult routeResult;

    /**
     * 当前的resultSet
     */
    @Getter(AccessLevel.NONE)
    private ResultSet currentResultSet;

    public ShardingStatement(final ShardingConnection connection) {
        this(connection, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, ResultSet.HOLD_CURSORS_OVER_COMMIT);
    }

    public ShardingStatement(final ShardingConnection connection, final int resultSetType, final int resultSetConcurrency, final int resultSetHoldability) {
        super(Statement.class);
        this.connection = connection;
        this.resultSetType = resultSetType;
        this.resultSetConcurrency = resultSetConcurrency;
        this.resultSetHoldability = resultSetHoldability;
    }


    @Override
    public ResultSet executeQuery(String sql) throws SQLException {
        ResultSet result = null;
      /*  try {
            List<ResultSet> resultSets = generateExecutor(sql).executeQuery();
            result = new ShardingResultSet(
                    resultSets, new MergeEngine(resultSets, (SelectStatement) routeResult.getSqlStatement()).merge());
        } finally {
            currentResultSet = null;
        }
        currentResultSet = result;*/
        return result;
    }

    @Override
    public int executeUpdate(String sql) throws SQLException {
        return 0;
    }

    @Override
    public boolean execute(String sql) throws SQLException {
        return false;
    }

    @Override
    public ResultSet getResultSet() throws SQLException {
        return null;
    }

    @Override
    public ResultSet getGeneratedKeys() throws SQLException {
        return null;
    }

    @Override
    public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
        return 0;
    }

    @Override
    public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
        return 0;
    }

    @Override
    public int executeUpdate(String sql, String[] columnNames) throws SQLException {
        return 0;
    }

    @Override
    public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
        return false;
    }

    @Override
    public boolean execute(String sql, int[] columnIndexes) throws SQLException {
        return false;
    }

    @Override
    public boolean execute(String sql, String[] columnNames) throws SQLException {
        return false;
    }

    private StatementExecutor generateExecutor(final String sql) throws SQLException {
        clearPrevious();
        routeResult = new StatementRoutingEngine(connection.getShardingContext()).route(sql);

        return null;

    }

    private void clearPrevious() throws SQLException {
        for (Statement each : routedStatements) {
            each.close();
        }
        routedStatements.clear();
    }

}
