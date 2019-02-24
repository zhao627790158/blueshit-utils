package sharding.rule;

import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.ToString;
import sharding.keygen.KeyGenerator;
import sharding.routing.strategy.ShardingStrategy;

import javax.sql.DataSource;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaoheng on 18/10/17.
 * 表规则配置
 */
@Getter
@ToString
public final class TableRule {

    //逻辑表名
    private final String logicTable;

    private final List<DataNode> actualDataNodes;

    private final ShardingStrategy databaseShardingStrategy;

    private final ShardingStrategy tableShardingStrategy;

    private final String generateKeyColumn;

    private final KeyGenerator keyGenerator;

    public TableRule(final String logicTable, final List<String> actualDataNodes, final Map<String, DataSource> dataSourceMap,
                     final ShardingStrategy databaseShardingStrategy, final ShardingStrategy tableShardingStrategy, final String generateKeyColumn, final KeyGenerator keyGenerator) {
        this.logicTable = logicTable;
        this.actualDataNodes = null == actualDataNodes || actualDataNodes.isEmpty() ? generateDataNodes(logicTable, dataSourceMap) : generateDataNodes(actualDataNodes, dataSourceMap);
        this.databaseShardingStrategy = databaseShardingStrategy;
        this.tableShardingStrategy = tableShardingStrategy;
        this.generateKeyColumn = generateKeyColumn;
        this.keyGenerator = keyGenerator;
    }

    /**
     * 生成数据节点
     * @param logicTable
     * @param dataSourceMap
     * @return
     */
    private List<DataNode> generateDataNodes(String logicTable, Map<String, DataSource> dataSourceMap) {
        List<DataNode> result = new LinkedList<>();
        for (String each : dataSourceMap.keySet()) {
            result.add(new DataNode(each, logicTable));
        }
        return result;
    }

    private List<DataNode> generateDataNodes(final List<String> actualDataNodes, final Map<String, DataSource> dataSourceMap) {
        List<DataNode> result = new LinkedList<>();
        for (String each : actualDataNodes) {
            Preconditions.checkArgument(DataNode.isValidDataNode(each), String.format("Invalid format for actual data nodes: '%s'", each));
            DataNode dataNode = new DataNode(each);
            Preconditions.checkArgument(dataSourceMap.containsKey(dataNode.getDataSourceName()),
                    String.format("Cannot find data source name in sharding rule, invalid actual data node is: '%s'", each));
            result.add(dataNode);
        }
        return result;
    }


}
