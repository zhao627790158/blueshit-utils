package sharding.api.config;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import lombok.Getter;
import lombok.Setter;
import sharding.keygen.KeyGenerator;
import sharding.keygen.KeyGeneratorFactory;
import sharding.routing.strategy.ShardingStrategy;
import sharding.rule.TableRule;
import sharding.api.config.strategy.ShardingStrategyConfiguration;
import sharding.util.InlineExpressionParser;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaoheng on 18/10/17.
 * 表规则配置
 * <p>
 * TableRuleConfiguration tableRuleConfig = new TableRuleConfiguration();
 * tableRuleConfig.setLogicTable("logicTable");
 * tableRuleConfig.setActualDataNodes("ds${0..1}.table_${0..2}");
 * TableRule actual = tableRuleConfig.build(createDataSourceMap());
 * assertThat(actual.getActualDatasourceNames(), is((Collection<String>) Sets.newLinkedHashSet(Arrays.asList("ds0", "ds1"))));
 */
@Getter
@Setter
public class TableRuleConfiguration {

    //逻辑表名 "logicTable"
    private String logicTable;

    //数据节点信息
    private String actualDataNodes;

    //数据源分片策略
    private ShardingStrategyConfiguration databaseShardingStrategyConfig;

    //表分片策略
    private ShardingStrategyConfiguration tableShardingStrategyConfig;

    //全局唯一主键列名
    private String keyGeneratorColumnName;

    //全局主键生单类
    private String keyGeneratorClass;


    public TableRule build(final Map<String, DataSource> dataSourceMap) {
        Preconditions.checkNotNull(logicTable, "逻辑表名不能为空");
        //解析数据源
        List<String> actualDataNodes = new InlineExpressionParser(this.actualDataNodes).evaluate();
        ShardingStrategy databaseShardingStrategy = null == databaseShardingStrategyConfig ? null : databaseShardingStrategyConfig.build();
        ShardingStrategy tableShardingStrategy = null == tableShardingStrategyConfig ? null : tableShardingStrategyConfig.build();
        KeyGenerator keyGenerator = !Strings.isNullOrEmpty(keyGeneratorColumnName) && !Strings.isNullOrEmpty(keyGeneratorClass) ? KeyGeneratorFactory.newInstance(keyGeneratorClass) : null;
        return new TableRule(logicTable, actualDataNodes, dataSourceMap, databaseShardingStrategy, tableShardingStrategy, keyGeneratorColumnName, keyGenerator);
    }


}
