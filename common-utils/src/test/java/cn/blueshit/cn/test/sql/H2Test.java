package cn.blueshit.cn.test.sql;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Stopwatch;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhaoheng on 17/10/4.
 */
public class H2Test {

    private static JdbcTemplate jdbcTemplate;

    private static DataSource dataSource;

    static {
        Properties properties = new Properties();
        properties.put("url", "jdbc:h2:file:/Users/zhaoheng/local/h2/Mytest");
        properties.put("username", "");
        properties.put("password", "");
        try {
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Test
    public void test1() throws Exception {

        String dbSchema = "CREATE TABLE  my_test("
                + "`id` BIGINT NOT NULL AUTO_INCREMENT, "
                + "`success_count` INT(11),"
                + "`failed_count` INT(11),"
                + "`statistics_time` TIMESTAMP NOT NULL,"
                + "`creation_time` TIMESTAMP NOT NULL,"
                + "PRIMARY KEY (`id`));";


        Connection connection = dataSource.getConnection();
        DatabaseMetaData metaData = connection.getMetaData();
        try (ResultSet tables = metaData.getTables(null, null, "MY_TEST", new String[]{"TABLE"})) {
            if (tables.next()) {
                System.out.println("table  exist");
            } else {
                System.out.println("table not exist");
            }
        }

    }

    @Test
    public void testContains() {
        //TABLE_NAME 一样要区分大小写
        String countSql = "SELECT  count(*) from INFORMATION_SCHEMA.TABLES where TABLE_SCHEMA='PUBLIC' AND TABLE_NAME ='MY_TEST';";
        int count = jdbcTemplate.queryForObject(countSql, Integer.class);
        System.out.println(count);
    }

    @Test
    public void testInsert() {
        Stopwatch started = Stopwatch.createStarted();
        String[] test = new String[1000];
        for (int i = 0; i < 1000; i++) {
            test[i] = "INSERT INTO MY_TEST (SUCCESS_COUNT, FAILED_COUNT) VALUES (" + i + "," + i * 10 + ");";
        }
        int[] ints = jdbcTemplate.batchUpdate(test);
        /*System.out.println(JSON.toJSONString(ints));
        jdbcTemplate.update("INSERT INTO MY_TEST (SUCCESS_COUNT, FAILED_COUNT) VALUES (100,200);");*/

        System.out.println(started.elapsed(TimeUnit.MILLISECONDS));
    }


    @Test
    public void testGet() {
        Integer integer = jdbcTemplate.queryForObject("select count(*) from MY_TEST", Integer.class);
        System.out.println(integer);
    }

}
