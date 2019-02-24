package sharding.api.config;

import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.MapUtils;
import sharding.api.config.strategy.ShardingStrategyConfiguration;
import sharding.keygen.DefaultKeyGenerator;
import sharding.keygen.KeyGenerator;
import sharding.keygen.KeyGeneratorFactory;
import sharding.routing.strategy.ShardingStrategy;
import sharding.rule.ShardingRule;
import sharding.rule.TableRule;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by zhaoheng on 18/10/17.
 */
@Getter
@Setter
public class ShardingRuleConfiguration {

    private String defaultDataSourceName;

    private Collection<TableRuleConfiguration> tableRuleConfigs = new LinkedList<>();

    //add("t_order, t_order_item");
    private Collection<String> bindingTableGroups = new LinkedList<>();

    private ShardingStrategyConfiguration defaultDatabaseShardingStrategyConfig;

    private ShardingStrategyConfiguration defaultTableShardingStrategyConfig;

    private String defaultKeyGeneratorClass;

    private Collection<MasterSlaveRuleConfiguration> masterSlaveRuleConfigs = new LinkedList<>();

    public ShardingRule build(final Map<String, DataSource> dataSourceMap) throws SQLException {
        Preconditions.checkState(MapUtils.isNotEmpty(dataSourceMap), "dataSources cannot be null.");
        processDataSourceMapWithMasterSlave(dataSourceMap);
        Collection<TableRule> tableRules = new LinkedList<>();
        for (TableRuleConfiguration each : tableRuleConfigs) {
            tableRules.add(each.build(dataSourceMap));
        }
        //默认的数据库分片规则
        ShardingStrategy defaultDatabaseShardingStrategy = null == defaultDatabaseShardingStrategyConfig ? null : defaultDatabaseShardingStrategyConfig.build();
        //默认的表分片规则
        ShardingStrategy defaultTableShardingStrategy = null == defaultTableShardingStrategyConfig ? null : defaultTableShardingStrategyConfig.build();
        KeyGenerator keyGenerator = KeyGeneratorFactory.newInstance(null == defaultKeyGeneratorClass ? DefaultKeyGenerator.class.getName() : defaultKeyGeneratorClass);
        return new ShardingRule(dataSourceMap, defaultDataSourceName, tableRules, bindingTableGroups, defaultDatabaseShardingStrategy, defaultTableShardingStrategy, keyGenerator);
    }

    /**
     * 主从配置使用,暂时忽略
     *
     * @param dataSourceMap
     */
    private void processDataSourceMapWithMasterSlave(Map<String, DataSource> dataSourceMap) {
        for (MasterSlaveRuleConfiguration each : masterSlaveRuleConfigs) {
            //todo
           /* dataSourceMap.put(each.getName(), MasterSlaveDataSourceFactory.createDataSource(dataSourceMap, each));
            dataSourceMap.remove(each.getMasterDataSourceName());
            for (String slaveDataSourceName : each.getSlaveDataSourceNames()) {
                dataSourceMap.remove(slaveDataSourceName);
            }*/
        }
    }


}
