package sharding.jdbc.unsupported;

import sharding.jdbc.adapter.WrapperAdapter;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;

public abstract class AbstractUnsupportedOperationDataSource extends WrapperAdapter implements DataSource {

    @Override
    public final int getLoginTimeout() throws SQLException {
        throw new SQLFeatureNotSupportedException("unsupported getLoginTimeout()");
    }

    @Override
    public final void setLoginTimeout(final int seconds) throws SQLException {
        throw new SQLFeatureNotSupportedException("unsupported setLoginTimeout(int seconds)");
    }
}
