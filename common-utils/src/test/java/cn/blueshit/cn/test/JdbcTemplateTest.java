package cn.blueshit.cn.test;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Created by zhaoheng on 17/10/4.
 */
public class JdbcTemplateTest {

    private JdbcTemplate jdbcTemplate;


    public JdbcTemplateTest(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


/*
    public boolean contains(String tableName) {
        int count = jdbcTemplate.queryForInt(
                "select count(*) from information_schema.tables " +
                        " where table_schema='PUBLIC' and table_name='" + tableName.toUpperCase() + "';");
        return count == 1 ? true : false;
    }*/






}
