package sharding.jdbc.adapter;

import lombok.Getter;
import sharding.jdbc.unsupported.AbstractUnsupportedOperationConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by zhaoheng on 18/10/17.
 */
public abstract class AbstractConnectionAdapter extends AbstractUnsupportedOperationConnection {


    @Getter
    private final Map<String, Connection> cachedConnections = new HashMap<>();

    private boolean autoCommit = true;

    private boolean readOnly = true;

    private boolean closed;

    private int transactionIsolation = TRANSACTION_READ_UNCOMMITTED;

    @Override
    public final boolean getAutoCommit() throws SQLException {
        return autoCommit;
    }

    @Override
    public final void setAutoCommit(final boolean autoCommit) throws SQLException {
        this.autoCommit = autoCommit;
        recordMethodInvocation(Connection.class, "setAutoCommit", new Class[]{boolean.class}, new Object[]{autoCommit});
        for (Connection each : cachedConnections.values()) {
            each.setAutoCommit(autoCommit);
        }
    }

    @Override
    public final void commit() throws SQLException {
        Collection<SQLException> exceptions = new LinkedList<>();
        for (Connection each : cachedConnections.values()) {
            try {
                each.commit();
            } catch (final SQLException ex) {
                exceptions.add(ex);
            }
        }
        throwSQLExceptionIfNecessary(exceptions);
    }

    @Override
    public final void rollback() throws SQLException {
        Collection<SQLException> exceptions = new LinkedList<>();
        for (Connection each : cachedConnections.values()) {
            try {
                each.rollback();
            } catch (final SQLException ex) {
                exceptions.add(ex);
            }
        }
        throwSQLExceptionIfNecessary(exceptions);
    }

    @Override
    public void close() throws SQLException {
        closed = true;
        Collection<SQLException> exceptions = new LinkedList<>();
        for (Connection each : cachedConnections.values()) {
            try {
                each.close();
            } catch (final SQLException ex) {
                exceptions.add(ex);
            }
        }
        throwSQLExceptionIfNecessary(exceptions);
    }

    @Override
    public final boolean isClosed() throws SQLException {
        return closed;
    }

    @Override
    public final boolean isReadOnly() throws SQLException {
        return readOnly;
    }

    @Override
    public final void setReadOnly(final boolean readOnly) throws SQLException {
        this.readOnly = readOnly;
        recordMethodInvocation(Connection.class, "setReadOnly", new Class[]{boolean.class}, new Object[]{readOnly});
        for (Connection each : cachedConnections.values()) {
            each.setReadOnly(readOnly);
        }
    }

    @Override
    public final int getTransactionIsolation() throws SQLException {
        return transactionIsolation;
    }

    @Override
    public final void setTransactionIsolation(final int level) throws SQLException {
        transactionIsolation = level;
        recordMethodInvocation(Connection.class, "setTransactionIsolation", new Class[]{int.class}, new Object[]{level});
        for (Connection each : cachedConnections.values()) {
            each.setTransactionIsolation(level);
        }
    }


    // ------- Consist with MySQL driver implementation -------

    @Override
    public SQLWarning getWarnings() throws SQLException {
        return null;
    }

    @Override
    public void clearWarnings() throws SQLException {
    }

    @Override
    public final int getHoldability() throws SQLException {
        return ResultSet.CLOSE_CURSORS_AT_COMMIT;
    }

    @Override
    public final void setHoldability(final int holdability) throws SQLException {
    }

}
