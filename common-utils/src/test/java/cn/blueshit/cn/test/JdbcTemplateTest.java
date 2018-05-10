package cn.blueshit.cn.test;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.fastjson.JSON;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by zhaoheng on 17/10/4.
 */
public class JdbcTemplateTest {

    /**
     * 使用配置文件构建Druid数据源.
     */
    public static final int DRUID_MYSQL_MASTER = 1;
    /**
     * 使用配置文件构建Druid数据源.
     */
    public static final int DRUID_MYSQL_SALVE = 2;


    private JdbcTemplate jdbcTemplate;

    private DataSource dataSource;


    @Before
    public void init() throws Exception {
        dataSource = getDataSource(DRUID_MYSQL_MASTER);
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Test
    public void testSleep() throws Exception {
        Connection connection = jdbcTemplate.getDataSource().getConnection();
        System.out.println(connection);
        connection.close();
        Connection connection1 = jdbcTemplate.getDataSource().getConnection();
        System.out.println(connection1);
        connection1.close();
        try {
            List<Map<String, Object>> maps = jdbcTemplate.queryForList("select sleep(10)");
            //List<Map<String, Object>> maps1 = jdbcTemplate.queryForList("select sleep(1)");
            System.out.println(JSON.toJSONString(maps));
        } catch (Exception e) {
            //client的链接将会被断开
            e.printStackTrace();
        }

        System.out.println(dataSource.getConnection());
    }

    @Test
    public void testSum() throws Exception {
        DataSource dataSource = getDataSource(DRUID_MYSQL_SALVE);
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select sum(order_id) from mta_order_mock_genpay");
        System.out.println(JSON.toJSONString(maps));
    }

    @Test
    public void testSelectAll() {
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select * From mta_order_mock_genpay WHERE  1 =1;");
        System.out.println(JSON.toJSONString(maps));
    }


    public static final DataSource getDataSource(int sourceType) throws Exception {
        DataSource dataSource = null;

        Properties p = new Properties();
        p.put("driverClassName", "com.mysql.jdbc.Driver");
        p.put("initialSize", "1");
        p.put("minIdle", "1");
        p.put("maxActive", "1");
        p.put("maxWait", "60000");
        p.put("timeBetweenEvictionRunsMillis", "60000");
        p.put("minEvictableIdleTimeMillis", "300000");
        p.put("validationQuery", "SELECT 'x' from dual");
        p.put("testWhileIdle", "true");
        p.put("testOnBorrow", "false");
        p.put("testOnReturn", "false");
        p.put("poolPreparedStatements", "true");
        p.put("maxPoolPreparedStatementPerConnectionSize", "20");
        p.put("filters", "stat");


        switch (sourceType) {
            case DRUID_MYSQL_MASTER:
                p.put("url", "jdbc:mysql://10.4.243.111:3306/mobile_service?useUnicode=true&characterEncoding=utf8&socketTimeout=5000");
                p.put("username", "q3boy");
                p.put("password", "123");

                dataSource = DruidDataSourceFactory.createDataSource(p);
                break;
            case DRUID_MYSQL_SALVE:
                p.put("url", "jdbc:mysql://10.4.243.111:3306/mobile_service?useUnicode=true&characterEncoding=utf8&socketTimeout=100");
                p.put("username", "q3boy");
                p.put("password", "123");

                dataSource = DruidDataSourceFactory.createDataSource(p);
                break;
        }
        return dataSource;
    }


}
