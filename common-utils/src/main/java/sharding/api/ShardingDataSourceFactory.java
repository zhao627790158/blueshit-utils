package sharding.api;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import sharding.api.config.ShardingRuleConfiguration;
import sharding.jdbc.core.ShardingDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by zhaoheng on 18/10/17.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ShardingDataSourceFactory {

    /**
     * 创建分片数据源
     * @param dataSourceMap
     * @param shardingRuleConfig
     * @return
     * @throws SQLException
     */
    public static DataSource createDataSource(final Map<String, DataSource> dataSourceMap, final ShardingRuleConfiguration shardingRuleConfig) throws SQLException {
        return new ShardingDataSource(shardingRuleConfig.build(dataSourceMap));
    }

}
