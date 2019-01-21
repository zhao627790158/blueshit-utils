package sharding.rule;

import com.google.common.base.Optional;
import com.google.common.base.Strings;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import sharding.exception.ShardingJdbcException;
import sharding.keygen.KeyGenerator;
import sharding.routing.strategy.ShardingStrategy;
import sharding.routing.strategy.none.NoneShardingStrategy;
import sharding.util.StringUtil;

import javax.sql.DataSource;
import java.util.*;

/**
 * Created by zhaoheng on 18/10/17.\
 * 数据库和表分片规则配置
 */
@RequiredArgsConstructor
@Getter
public class ShardingRule {

    private final Map<String, DataSource> dataSourceMap;

    private final String defaultDataSourceName;

    private final Collection<TableRule> tableRules;

    private final Collection<BindingTableRule> bindingTableRules = new LinkedList<>();

    private final ShardingStrategy defaultDatabaseShardingStrategy;

    private final ShardingStrategy defaultTableShardingStrategy;

    private final KeyGenerator defaultKeyGenerator;

    public ShardingRule(final Map<String, DataSource> dataSourceMap, final String defaultDataSourceName, final Collection<TableRule> tableRules, final Collection<String> bindingTableGroups,
                        final ShardingStrategy defaultDatabaseShardingStrategy, final ShardingStrategy defaultTableShardingStrategy, final KeyGenerator defaultKeyGenerator) {
        this.dataSourceMap = dataSourceMap;
        this.defaultDataSourceName = getDefaultDataSourceName(dataSourceMap, defaultDataSourceName);
        this.tableRules = tableRules;
        for (String group : bindingTableGroups) {
            List<TableRule> tableRulesForBinding = new LinkedList<>();
            for (String logicTableNameForBindingTable : StringUtil.splitWithComma(group)) {
                tableRulesForBinding.add(getTableRule(logicTableNameForBindingTable));
            }
            this.bindingTableRules.add(new BindingTableRule(tableRulesForBinding));
        }
        this.defaultDatabaseShardingStrategy = null == defaultDatabaseShardingStrategy ? new NoneShardingStrategy() : defaultDatabaseShardingStrategy;
        this.defaultTableShardingStrategy = null == defaultTableShardingStrategy ? new NoneShardingStrategy() : defaultTableShardingStrategy;
        this.defaultKeyGenerator = defaultKeyGenerator;
    }


    private String getDefaultDataSourceName(final Map<String, DataSource> dataSourceMap, final String defaultDataSourceName) {
        if (1 == dataSourceMap.size()) {
            return dataSourceMap.entrySet().iterator().next().getKey();
        }
        if (Strings.isNullOrEmpty(defaultDataSourceName)) {
            return null;
        }
        return defaultDataSourceName;
    }

    public TableRule getTableRule(final String logicTableName) {
        Optional<TableRule> tableRule = tryFindTableRule(logicTableName);
        if (tableRule.isPresent()) {
            return tableRule.get();
        }
        if (null != defaultDataSourceName) {
            return createTableRuleWithDefaultDataSource(logicTableName);
        }
        throw new ShardingJdbcException("Cannot find table rule and default data source with logic table: '%s'", logicTableName);
    }

    public Optional<TableRule> tryFindTableRule(final String logicTableName) {
        for (TableRule each : tableRules) {
            if (each.getLogicTable().equalsIgnoreCase(logicTableName)) {
                return Optional.of(each);
            }
        }
        return Optional.absent();
    }

    private TableRule createTableRuleWithDefaultDataSource(final String logicTableName) {
        Map<String, DataSource> defaultDataSourceMap = new HashMap<>(1, 1);
        defaultDataSourceMap.put(defaultDataSourceName, dataSourceMap.get(defaultDataSourceName));
        //todo 这里的意义是什么?
        /*TableRuleConfiguration config = new TableRuleConfiguration();
        config.setLogicTable(logicTableName);
        config.setDatabaseShardingStrategyConfig(new NoneShardingStrategyConfiguration());
        config.setTableShardingStrategyConfig(new NoneShardingStrategyConfiguration());*/
        return new TableRule(logicTableName, null, defaultDataSourceMap, null, null, null, null);
    }

}
