package cn.blueshit.cn.test.sharding;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlSchemaStatVisitor;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.google.common.base.Joiner;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhaoheng on 18/10/17.
 */
public class Test {
    @org.junit.Test
    public void test() {
        Map<String, DataSource> dataSourceMap = new HashMap<>();

        /*BasicDataSource dataSource1 = new BasicDataSource();
        dataSource2.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource1.setUrl("jdbc:mysql://localhost:3306/ds_0");
        dataSource1.setUsername("root");
        dataSource1.setPassword("");
        dataSourceMap.put("ds_0", dataSource1);

        BasicDataSource dataSource2 = new BasicDataSource();
        dataSource2.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource2.setUrl("jdbc:mysql://localhost:3306/ds_1");
        dataSource2.setUsername("root");
        dataSource2.setPassword("");
        dataSourceMap.put("ds_1", dataSource2);

        TableRuleConfiguration orderTableRuleConfig = new TableRuleConfiguration();
        orderTableRuleConfig.setLogicTable("t_order");
        orderTableRuleConfig.setActualDataNodes("ds_${0..1}.t_order_${[0, 1]}");

        orderTableRuleConfig.setDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("user_id", "ds_${user_id % 2}"));
        orderTableRuleConfig.setTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("order_id", "t_order_${order_id % 2}"));

        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        shardingRuleConfig.getTableRuleConfigs().add(orderTableRuleConfig);

        // config order_item table rule...

        DataSource dataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig);*/
    }


    @org.junit.Test
    public void test1() {
        System.out.println(Joiner.on("").join("{it -> \"", "ds_${user_id % 2}".trim(), "\"}"));
    }

    @org.junit.Test
    public void test2() {
        String sql = "select * from user where order_id = 111 order by id";

        // 新建 MySQL Parser
        SQLStatementParser parser = new MySqlStatementParser(sql);

        // 使用Parser解析生成AST，这里SQLStatement就是AST
        SQLStatement statement = parser.parseStatement();

        // 使用visitor来访问AST
        MySqlSchemaStatVisitor visitor = new MySqlSchemaStatVisitor();
        statement.accept(visitor);

        System.out.println(visitor.getColumns());
        System.out.println(visitor.getOrderByColumns());
        System.out.println(visitor.getTables());

    }
}
