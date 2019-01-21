package sharding.jdbc.core;

import sharding.constant.ShardingProperties;
import sharding.constant.ShardingPropertiesConstant;
import sharding.executor.ExecutorEngine;
import sharding.jdbc.adapter.AbstractDataSourceAdapter;
import sharding.jdbc.core.connection.ShardingConnection;
import sharding.rule.ShardingRule;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by zhaoheng on 18/10/17.
 */
public class ShardingDataSource extends AbstractDataSourceAdapter implements AutoCloseable {


    //分片配置文件
    private ShardingProperties shardingProperties;

    //sql执行引擎
    private ExecutorEngine executorEngine;

    //分片上线文信息
    private ShardingContext shardingContext;

    public ShardingDataSource(final ShardingRule shardingRule) throws SQLException {
        this(shardingRule, new Properties());
    }

    public ShardingDataSource(final ShardingRule shardingRule, final Properties props) throws SQLException {
        super(shardingRule.getDataSourceMap().values());
        shardingProperties = new ShardingProperties(null == props ? new Properties() : props);
        int executorSize = shardingProperties.getValue(ShardingPropertiesConstant.EXECUTOR_SIZE);
        executorEngine = new ExecutorEngine(executorSize);
        boolean showSQL = shardingProperties.getValue(ShardingPropertiesConstant.SQL_SHOW);
        shardingContext = new ShardingContext(shardingRule, getDatabaseType(), executorEngine, showSQL);
    }


    @Override
    public Connection getConnection() throws SQLException {
        return new ShardingConnection(shardingContext);
    }

    @Override
    public void close() throws Exception {
        executorEngine.close();
    }
}
