package sharding.jdbc.core;

import lombok.Getter;
import sharding.jdbc.adapter.AbstractDataSourceAdapter;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

@Getter
public class MasterSlaveDataSource extends AbstractDataSourceAdapter {

    private static final ThreadLocal<Boolean> DML_FLAG = new ThreadLocal<Boolean>() {

        @Override
        protected Boolean initialValue() {
            return false;
        }
    };

    public MasterSlaveDataSource(Collection<DataSource> dataSources) throws SQLException {
        super(dataSources);
    }

    @Override
    public Connection getConnection() throws SQLException {
        return null;
    }
}