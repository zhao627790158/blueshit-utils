package cn.blueshit.cn.test.sql;

import cn.zh.blueshit.sql.DefaultSQLRewrite;
import cn.zh.blueshit.sql.SQLParsedResult;
import cn.zh.blueshit.sql.SQLParser;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Pattern;

/**
 * Created by zhaoheng on 17/4/10.
 */
public class ParseTest {

    private final static String PARAM_PATTERN_EXP = "\\?\\d+";
    private final static Pattern PARAM_PATTERN = Pattern.compile(PARAM_PATTERN_EXP);

    private static SortedMap<Integer, Object> parameters = new TreeMap<Integer, Object>(new Comparator<Integer>() {
        public int compare(Integer o1, Integer o2) {
            return o1.compareTo(o2);
        }
    });

    static {
        parameters.put(1, 10);
        parameters.put(2, "name");
    }


    @Test
    public void test1() {
        DefaultSQLRewrite rewriter = new DefaultSQLRewrite();
        SQLParsedResult result = SQLParser
                .parse("select a,b /*comment**/ from table where `c` = 1 limit 10,10 #this is comment");

        String newSql = rewriter.rewrite(result, "table", "table1");
        System.out.println(newSql);
    }

    @Test
    public void test2() {
        Sql rawSql = new Sql("select * from test t where t.id =? and t.name=?", true, parameters);
        String sqlWithParameterIndex = SqlUtils.getSqlWithParameterIndex(rawSql.getText());
        System.out.println(sqlWithParameterIndex);
        SqlParserAndBuilder sqlParserAndBuilder = new SqlParserAndBuilder(sqlWithParameterIndex);
        List<TableValue> tableValues = sqlParserAndBuilder.getTableValues();
        //对?1, ?2这样的参数，找到index对应的真实值填充到TableValue中作为真正分片值使用
        for (TableValue tableValue : tableValues) {
            for (ColumnValue columnValue : tableValue.getColumnValues()) {
                if (PARAM_PATTERN.matcher(columnValue.getColumnValue()).matches()) {
                    Integer parameterIndex = Integer.valueOf(columnValue.getColumnValue().substring(1));
                    columnValue.setColumnValue(String.valueOf(rawSql.getParameters().get(parameterIndex)));
                }
            }
        }
    }
}
