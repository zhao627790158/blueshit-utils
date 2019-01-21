package sharding.jdbc.adapter;

import lombok.RequiredArgsConstructor;
import sharding.jdbc.unsupported.AbstractUnsupportedOperationStatement;

import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;

@RequiredArgsConstructor
public abstract class AbstractStatementAdapter extends AbstractUnsupportedOperationStatement {

    private final Class<? extends Statement> targetClass;

    private boolean closed;

    private boolean poolable;

    private int fetchSize;

    @Override
    public final void close() throws SQLException {
        closed = true;
        Collection<SQLException> exceptions = new LinkedList<>();
        for (Statement each : getRoutedStatements()) {
            try {
                each.close();
            } catch (final SQLException ex) {
                exceptions.add(ex);
            }
        }
        getRoutedStatements().clear();
        throwSQLExceptionIfNecessary(exceptions);
    }

    @Override
    public final boolean isClosed() throws SQLException {
        return closed;
    }

    @Override
    public final boolean isPoolable() throws SQLException {
        return poolable;
    }

    @Override
    public final void setPoolable(final boolean poolable) throws SQLException {
        this.poolable = poolable;
        if (getRoutedStatements().isEmpty()) {
            recordMethodInvocation(targetClass, "setPoolable", new Class[]{boolean.class}, new Object[]{poolable});
            return;
        }
        for (Statement each : getRoutedStatements()) {
            each.setPoolable(poolable);
        }
    }

    @Override
    public final int getFetchSize() throws SQLException {
        return fetchSize;
    }

    @Override
    public final void setFetchSize(final int rows) throws SQLException {
        this.fetchSize = rows;
        if (getRoutedStatements().isEmpty()) {
            recordMethodInvocation(targetClass, "setFetchSize", new Class[]{int.class}, new Object[]{rows});
            return;
        }
        for (Statement each : getRoutedStatements()) {
            each.setFetchSize(rows);
        }
    }

    @Override
    public final void setEscapeProcessing(final boolean enable) throws SQLException {
        if (getRoutedStatements().isEmpty()) {
            recordMethodInvocation(targetClass, "setEscapeProcessing", new Class[]{boolean.class}, new Object[]{enable});
            return;
        }
        for (Statement each : getRoutedStatements()) {
            each.setEscapeProcessing(enable);
        }
    }

    @Override
    public final void cancel() throws SQLException {
        for (Statement each : getRoutedStatements()) {
            each.cancel();
        }
    }

    @Override
    public final int getUpdateCount() throws SQLException {
        long result = 0;
        boolean hasResult = false;
        for (Statement each : getRoutedStatements()) {
            if (each.getUpdateCount() > -1) {
                hasResult = true;
            }
            result += each.getUpdateCount();
        }
        if (result > Integer.MAX_VALUE) {
            result = Integer.MAX_VALUE;
        }
        return hasResult ? Long.valueOf(result).intValue() : -1;
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        return null;
    }

    @Override
    public void clearWarnings() throws SQLException {
    }

    @Override
    public final boolean getMoreResults() throws SQLException {
        return false;
    }

    @Override
    public final boolean getMoreResults(final int current) throws SQLException {
        return false;
    }

    @Override
    public final int getMaxFieldSize() throws SQLException {
        return getRoutedStatements().isEmpty() ? 0 : getRoutedStatements().iterator().next().getMaxFieldSize();
    }

    @Override
    public final void setMaxFieldSize(final int max) throws SQLException {
        if (getRoutedStatements().isEmpty()) {
            recordMethodInvocation(targetClass, "setMaxFieldSize", new Class[]{int.class}, new Object[]{max});
            return;
        }
        for (Statement each : getRoutedStatements()) {
            each.setMaxFieldSize(max);
        }
    }

    // TODO Confirm MaxRows for multiple databases is need special handle. eg: 10 statements maybe MaxRows / 10
    @Override
    public final int getMaxRows() throws SQLException {
        return getRoutedStatements().isEmpty() ? -1 : getRoutedStatements().iterator().next().getMaxRows();
    }

    @Override
    public final void setMaxRows(final int max) throws SQLException {
        if (getRoutedStatements().isEmpty()) {
            recordMethodInvocation(targetClass, "setMaxRows", new Class[]{int.class}, new Object[]{max});
            return;
        }
        for (Statement each : getRoutedStatements()) {
            each.setMaxRows(max);
        }
    }

    @Override
    public final int getQueryTimeout() throws SQLException {
        return getRoutedStatements().isEmpty() ? 0 : getRoutedStatements().iterator().next().getQueryTimeout();
    }

    @Override
    public final void setQueryTimeout(final int seconds) throws SQLException {
        if (getRoutedStatements().isEmpty()) {
            recordMethodInvocation(targetClass, "setQueryTimeout", new Class[]{int.class}, new Object[]{seconds});
            return;
        }
        for (Statement each : getRoutedStatements()) {
            each.setQueryTimeout(seconds);
        }
    }

    protected abstract Collection<? extends Statement> getRoutedStatements();

}