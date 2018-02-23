package cn.blueshit.cn.test.sql;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.ExpressionVisitor;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.Pivot;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.util.deparser.ExpressionDeParser;
import net.sf.jsqlparser.util.deparser.SelectDeParser;

public class JSQLTest {
    public static void main(String[] args) throws JSQLParserException {
        String sql = "select * from presto.books as t1 left join default.hbooks as t2 on t1.id=t2.id";
        Select select = (Select) CCJSqlParserUtil.parse(sql);
        StringBuilder buffer = new StringBuilder();
        ExpressionDeParser expressionDeParser = new ExpressionDeParser();
        SelectDeParser deparser = new MySelectDeParser(expressionDeParser, buffer);
        expressionDeParser.setSelectVisitor(deparser);
        expressionDeParser.setBuffer(buffer);
        select.getSelectBody().accept(deparser); //End of value modification
        System.out.println(buffer.toString());
    }
}

class MySelectDeParser extends SelectDeParser {
    public MySelectDeParser(ExpressionVisitor expressionVisitor, StringBuilder buffer) {
        super(expressionVisitor, buffer);
    }

    @Override
    public void visit(Table tableName) {
        System.out.println("getSchemaName:" + tableName.getSchemaName());
        String schema = tableName.getSchemaName();
        tableName.setSchemaName("mysql" + "." + schema);
        StringBuilder buffer = getBuffer();
        buffer.append(tableName.getFullyQualifiedName());
        Pivot pivot = tableName.getPivot();
        if (pivot != null) {
            pivot.accept(this);
        }
        Alias alias = tableName.getAlias();
        if (alias != null) {
            buffer.append(alias);
        }
    }
}