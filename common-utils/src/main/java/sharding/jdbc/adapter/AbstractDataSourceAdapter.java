package sharding.jdbc.adapter;

import com.google.common.base.Preconditions;
import lombok.Getter;
import sharding.constant.DatabaseType;
import sharding.jdbc.unsupported.AbstractUnsupportedOperationDataSource;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Collection;
import java.util.logging.Logger;

/**
 * Created by zhaoheng on 18/10/17.
 */
public abstract class AbstractDataSourceAdapter extends AbstractUnsupportedOperationDataSource {

    @Getter
    private final DatabaseType databaseType;

    private PrintWriter logWriter = new PrintWriter(System.out);


    public AbstractDataSourceAdapter(final Collection<DataSource> dataSources) throws SQLException {
        databaseType = getDatabaseType(dataSources);
    }

    protected DatabaseType getDatabaseType(final Collection<DataSource> dataSources) throws SQLException {
        DatabaseType result = null;
        for (DataSource each : dataSources) {
            DatabaseType databaseType = getDatabaseType(each);
            Preconditions.checkState(null == result || result.equals(databaseType), String.format("Database type inconsistent with '%s' and '%s'", result, databaseType));
            result = databaseType;
        }
        return result;
    }

    private DatabaseType getDatabaseType(final DataSource dataSource) throws SQLException {
        if (dataSource instanceof AbstractDataSourceAdapter) {
            return ((AbstractDataSourceAdapter) dataSource).databaseType;
        }
        try (Connection connection = dataSource.getConnection()) {
            return DatabaseType.valueFrom(connection.getMetaData().getDatabaseProductName());
        }
    }

    @Override
    public final PrintWriter getLogWriter() throws SQLException {
        return logWriter;
    }

    @Override
    public final void setLogWriter(final PrintWriter out) throws SQLException {
        this.logWriter = out;
    }

    @Override
    public final Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    }

    @Override
    public final Connection getConnection(final String username, final String password) throws SQLException {
        return getConnection();
    }

}
