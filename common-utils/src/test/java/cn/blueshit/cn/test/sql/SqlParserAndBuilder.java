
package cn.blueshit.cn.test.sql;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.ItemsListVisitor;
import net.sf.jsqlparser.expression.operators.relational.MultiExpressionList;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.statement.update.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by wangjinsi on 2015-09-28.
 */
public class SqlParserAndBuilder {

  private Logger logger = LoggerFactory.getLogger(getClass());

  private final String suffixPlaceholder = "#suffix#";

  private String sql;

  private ParseResult parseResult;

  private Statement statement;

  private List<String> tableNames;

  private List<TableValue> tableValues;

  private String sqlWithPlaceholder;

  public SqlParserAndBuilder(String sql) {
    this.sql = sql;
    long startTime = System.currentTimeMillis();
    parse(this.sql);
    if (logger.isDebugEnabled()) {
      logger.debug("解析SQL耗时：" + (System.currentTimeMillis() - startTime) + "毫秒, sql=" + sql);
    }
  }

  public List<String> getTableNames() {
    return new ArrayList<String>(tableNames);
  }

  public List<TableValue> getTableValues() {
    List<TableValue> tempTableValues = null;
    if (this.tableValues != null) {
      tempTableValues = new ArrayList<TableValue>(this.tableValues.size());
      for (TableValue tempTableValue : this.tableValues) {
        tempTableValues.add(tempTableValue.clone());
      }
    }
    return tempTableValues;
  }

  public String createShardSqlText(int shardId, String separator) {
    return sqlWithPlaceholder.replaceAll(suffixPlaceholder, new StringBuilder(separator).append(shardId).toString());
  }

  private void parse(String sql) {
    try {
      statement = CCJSqlParserUtil.parse(sql);
    } catch (JSQLParserException e) {
      String msg = "解析SQL出错，请检查SQL是否正确,sql=" + sql;
      logger.error(msg, e);
      throw new RuntimeException(msg, e);
    }
    if (statement instanceof Insert) {
      parseResult = parseInsert((Insert) statement);
    } else if (statement instanceof Delete) {
      parseResult = parseDelete((Delete) statement);
    } else if (statement instanceof Update) {
      parseResult = parseUpdate((Update) statement);
    } else if (statement instanceof Select) {
      parseResult = parseSelect(((Select) statement).getSelectBody());
    } else {
      throw new UnsupportedOperationException("不支持的SQL类型：" + statement);
    }

    List<Table> tables = parseResult.getTables();
    //缓存TableName
    List<String> tempTableNames = new ArrayList<String>(tables.size());
    for (Table table : tables) {
      tempTableNames.add(table.getName());
    }
    this.tableNames = Collections.unmodifiableList(tempTableNames);

    //缓存TableValues
    this.tableValues = Collections.unmodifiableList(parseResult.geTableValues());

    //缓存SQL语句，并为表名加上后缀占位符，可以用来为表名加上shardId编号使用，使用时使用正确的值替换占位符即可
    //使用Map去重，防止同一个table多次被修改
    Map<Table, Table> tablesMap = new HashMap<Table, Table>();
    for (Table table : tables) {
      tablesMap.put(table, table);
    }
    StringBuilder tableName = new StringBuilder(100);//表名不超过100个字符
    for (Table table : tablesMap.keySet()) {
      if (tableName.length() > 0) {
        tableName.delete(0, tableName.length());
      }
      tableName.append(table.getName());
      tableName.append(suffixPlaceholder);
      table.setName(tableName.toString());
    }
    sqlWithPlaceholder = statement.toString();
  }

  private InsertParseResult parseInsert(final Insert insert) {
    final InsertParseResult insertParseResult = new InsertParseResult();
    insertParseResult.table = insert.getTable();
    insertParseResult.tableValue.setTableName(insert.getTable().getName());
    if (insert.getItemsList() != null) {
      insert.getItemsList().accept(new ItemsListVisitor() {
        @Override
        public void visit(SubSelect subSelect) {
          insertParseResult.itemChildren.add(parseSelect(subSelect.getSelectBody()));
        }

        @Override
        public void visit(ExpressionList expressionList) {
          for (int i = 0; i < expressionList.getExpressions().size(); i++) {
            Expression expression = expressionList.getExpressions().get(i);
            String value = null;
            if (expression instanceof DoubleValue) {
              value = String.valueOf(((DoubleValue) expression).getValue());
            } else if (expression instanceof LongValue) {
              value = String.valueOf(((LongValue) expression).getValue());
            } else if (expression instanceof DateValue) {
              value = String.valueOf(((DateValue) expression).getValue());
            } else if (expression instanceof TimeValue) {
              value = String.valueOf(((TimeValue) expression).getValue());
            } else if (expression instanceof TimestampValue) {
              value = String.valueOf(((TimestampValue) expression).getValue());
            } else if (expression instanceof StringValue) {
              value = String.valueOf(((StringValue) expression).getValue());
            } else if (expression instanceof JdbcParameter) {
              value = expression.toString();
            } else if (expression instanceof SubSelect) {
              this.visit((SubSelect) expression);
            }
            if (value != null) {
              ColumnValue columnValue = new ColumnValue();
              columnValue.setColumnName(insert.getColumns().get(i).getColumnName());
              columnValue.setColumnValue(value);
              insertParseResult.tableValue.getColumnValues().add(columnValue);
            }
          }
        }

        @Override
        public void visit(MultiExpressionList multiExprList) {
          throw new UnsupportedOperationException("不支持一个SQL语句中使用多条Insert语句");
        }
      });
    }
    return insertParseResult;
  }

  private DeleteParseResult parseDelete(Delete delete) {
    DeleteParseResult deleteParseResult = new DeleteParseResult();
    deleteParseResult.table = delete.getTable();
    deleteParseResult.tableValue.setTableName(delete.getTable().getName());
    if (delete.getWhere() != null) {
      WhereParseResult whereParseResult = parseWhere(delete.getWhere());
      for (EqualsColumnValue equalsColumnValue : whereParseResult.equalsColumnValues) {
        deleteParseResult.tableValue.getColumnValues().add(equalsColumnValue);
      }
      if (!whereParseResult.children.isEmpty()) {
        deleteParseResult.whereChildren.addAll(whereParseResult.children);
      }
    }
    return deleteParseResult;
  }

  private UpdateParseResult parseUpdate(Update update) {
    UpdateParseResult updateParseResult = new UpdateParseResult();
    updateParseResult.table = update.getTables().get(0);
    updateParseResult.tableValue.setTableName(update.getTables().get(0).getName());
    if (update.getWhere() != null) {
      WhereParseResult whereParseResult = parseWhere(update.getWhere());
      for (EqualsColumnValue equalsColumnValue : whereParseResult.equalsColumnValues) {
        updateParseResult.tableValue.getColumnValues().add(equalsColumnValue);
      }
      if (!whereParseResult.children.isEmpty()) {
        updateParseResult.whereChildren.addAll(whereParseResult.children);
      }
    }
    if (update.getExpressions() != null) {
      for (Expression expression : update.getExpressions()) {
        if (expression instanceof SubSelect) {
          updateParseResult.expressionChildren.add(parseSelect(((SubSelect) expression).getSelectBody()));
        }
      }
    }
    return updateParseResult;
  }

  private SelectParseResult parseSelect(final SelectBody selectBody) {
    final SelectParseResult selectParseResult = new SelectParseResult();
    selectBody.accept(new SelectVisitor() {
      @Override
      public void visit(PlainSelect plainSelect) {
        //解析select
        if (plainSelect.getSelectItems() != null) {
          for (SelectItem selectItem : plainSelect.getSelectItems()) {
            selectItem.accept(new SelectItemVisitor() {
              @Override
              public void visit(AllColumns allColumns) {
                //忽略，不可能出现分配值
              }

              @Override
              public void visit(AllTableColumns allTableColumns) {
                //忽略，不可能出现分配值
              }

              @Override
              public void visit(SelectExpressionItem selectExpressionItem) {
                if (selectExpressionItem.getExpression() instanceof SubSelect) {
                  SubSelect subSelect = (SubSelect) (selectExpressionItem.getExpression());
                  selectParseResult.selectItemChildren.add(parseSelect(subSelect.getSelectBody()));
                }
              }
            });
          }
        }
        //解析from
        if (plainSelect.getFromItem() != null) {
          if (plainSelect.getFromItem() instanceof Table) {
            selectParseResult.tables.add((Table) plainSelect.getFromItem());
          } else if (plainSelect.getFromItem() instanceof SubSelect) {
            selectParseResult.fromChildren.add(parseSelect(((SubSelect) plainSelect.getFromItem()).getSelectBody()));
          } else {
            throw new UnsupportedOperationException("不支持的SQL语法" + selectBody + " > " + plainSelect.getFromItem());
          }
          if (plainSelect.getJoins() != null) {
            for (Join join : plainSelect.getJoins()) {
              if (join.getRightItem() instanceof Table) {
                selectParseResult.tables.add((Table) (join.getRightItem()));
              } else if (join.getRightItem() instanceof SubSelect) {
                selectParseResult.fromChildren.add(parseSelect(((SubSelect) join.getRightItem()).getSelectBody()));
              } else {
                throw new UnsupportedOperationException("不支持的SQL语法" + selectBody + " > " + join);
              }
            }
          }
        }
        //解析where
        if (plainSelect.getWhere() != null) {
          WhereParseResult whereParseResult = parseWhere(plainSelect.getWhere());
          for (EqualsColumnValue equalsColumnValue : whereParseResult.equalsColumnValues) {
            Column column = equalsColumnValue.getColumn();
            if (selectParseResult.tables.size() == 1 &&
                (column.getTable().getName() == null || column.getTable().getName().isEmpty())) {
              TableValue tableValue = selectParseResult.tableValues.get(selectParseResult.tables.get(0).getName());
              tableValue.getColumnValues().add(equalsColumnValue);
            } else {
              for (Table table : selectParseResult.tables) {
                if (table.getAlias().getName().equals(column.getTable().getName())) {
                  TableValue tableValue = selectParseResult.tableValues.get(table.getName());
                  tableValue.getColumnValues().add(equalsColumnValue);
                }
              }
            }
          }
        }
      }

      @Override
      public void visit(SetOperationList setOpList) {
        throw new UnsupportedOperationException("不支持的SQL类型" + setOpList);
      }

      @Override
      public void visit(WithItem withItem) {
        throw new UnsupportedOperationException("不支持的SQL类型" + withItem);
      }
    });
    return selectParseResult;
  }

  private WhereParseResult parseWhere(Expression where) {
    final WhereParseResult whereParseResult = new WhereParseResult();
    where.accept(new AbstractExpressionVisitor() {
      @Override
      public void visit(EqualsTo equalsTo) {
        if (equalsTo.getLeftExpression() instanceof Column) {
          String value = null;
          if (equalsTo.getRightExpression() instanceof DoubleValue) {
            value = String.valueOf(((DoubleValue) equalsTo.getRightExpression()).getValue());
          } else if (equalsTo.getRightExpression() instanceof LongValue) {
            value = String.valueOf(((LongValue) equalsTo.getRightExpression()).getValue());
          } else if (equalsTo.getRightExpression() instanceof DateValue) {
            value = String.valueOf(((DateValue) equalsTo.getRightExpression()).getValue());
          } else if (equalsTo.getRightExpression() instanceof TimeValue) {
            value = String.valueOf(((TimeValue) equalsTo.getRightExpression()).getValue());
          } else if (equalsTo.getRightExpression() instanceof TimestampValue) {
            value = String.valueOf(((TimestampValue) equalsTo.getRightExpression()).getValue());
          } else if (equalsTo.getRightExpression() instanceof StringValue) {
            value = String.valueOf(((StringValue) equalsTo.getRightExpression()).getValue());
          } else if (equalsTo.getRightExpression() instanceof JdbcParameter) {
            value = equalsTo.getRightExpression().toString();
          } else {
            equalsTo.getLeftExpression().accept(this);
            equalsTo.getRightExpression().accept(this);
          }
          if (value != null) {
            Column column = (Column) equalsTo.getLeftExpression();
            EqualsColumnValue equalsColumnValue = new EqualsColumnValue();
            equalsColumnValue.setColumn(column);
            equalsColumnValue.setColumnName(column.getColumnName());
            equalsColumnValue.setColumnValue(value);
            whereParseResult.equalsColumnValues.add(equalsColumnValue);
          }
        }
      }

      @Override
      public void visit(SubSelect subSelect) {
        whereParseResult.children.add(parseSelect(subSelect.getSelectBody()));
      }
    });
    return whereParseResult;
  }

  private interface ParseResult {

    List<TableValue> geTableValues();

    List<Table> getTables();

  }

  private class InsertParseResult implements ParseResult {
    private Table table;
    private TableValue tableValue = new TableValue();
    private List<SelectParseResult> itemChildren = new ArrayList<SelectParseResult>();

    public List<TableValue> geTableValues() {
      List<TableValue> tableValues = new ArrayList<TableValue>();
      tableValues.add(tableValue);
      for (SelectParseResult selectParseResult : itemChildren) {
        tableValues.addAll(selectParseResult.geTableValues());
      }
      return tableValues;
    }

    @Override
    public List<Table> getTables() {
      List<Table> tables = new ArrayList<Table>();
      tables.add(table);
      for (SelectParseResult selectParseResult : itemChildren) {
        tables.addAll(selectParseResult.getTables());
      }
      return tables;
    }

    @Override
    public String toString() {
      return "InsertResult{" +
          "table=" + table +
          ", tableValue=" + tableValue +
          ", itemChildren=" + itemChildren +
          '}';
    }
  }

  private class UpdateParseResult implements ParseResult {
    private Table table;
    private TableValue tableValue = new TableValue();
    private List<SelectParseResult> whereChildren = new ArrayList<SelectParseResult>();
    private List<SelectParseResult> expressionChildren = new ArrayList<SelectParseResult>();

    public List<TableValue> geTableValues() {
      List<TableValue> tableValues = new ArrayList<TableValue>();
      tableValues.add(tableValue);
      for (SelectParseResult selectParseResult : whereChildren) {
        tableValues.addAll(selectParseResult.geTableValues());
      }
      for (SelectParseResult selectParseResult : expressionChildren) {
        tableValues.addAll(selectParseResult.geTableValues());
      }
      return tableValues;
    }

    @Override
    public List<Table> getTables() {
      List<Table> tables = new ArrayList<Table>();
      tables.add(table);
      for (SelectParseResult selectParseResult : whereChildren) {
        tables.addAll(selectParseResult.getTables());
      }
      for (SelectParseResult selectParseResult : expressionChildren) {
        tables.addAll(selectParseResult.getTables());
      }
      return tables;
    }

    @Override
    public String toString() {
      return "UpdateResult{" +
          "table=" + table +
          ", whereChildren=" + whereChildren +
          ", expressionChildren=" + expressionChildren +
          '}';
    }
  }

  private class DeleteParseResult implements ParseResult {
    private Table table;
    private TableValue tableValue = new TableValue();
    private List<SelectParseResult> whereChildren = new ArrayList<SelectParseResult>();

    public List<TableValue> geTableValues() {
      List<TableValue> tableValues = new ArrayList<TableValue>();
      tableValues.add(tableValue);
      for (SelectParseResult selectParseResult : whereChildren) {
        tableValues.addAll(selectParseResult.geTableValues());
      }
      return tableValues;
    }

    @Override
    public List<Table> getTables() {
      List<Table> tables = new ArrayList<Table>();
      tables.add(table);
      for (SelectParseResult selectParseResult : whereChildren) {
        tables.addAll(selectParseResult.getTables());
      }
      return tables;
    }

    @Override
    public String toString() {
      return "DeleteResult{" +
          "table=" + table +
          ", tableValue=" + tableValue +
          ", whereChildren=" + whereChildren +
          '}';
    }
  }

  private class SelectParseResult implements ParseResult {
    private List<Table> tables = new ArrayList<Table>();
    private SortedMap<String, TableValue> tableValues = new TableValueTreeMap();
    private List<SelectParseResult> whereChildren = new ArrayList<SelectParseResult>();
    private List<SelectParseResult> fromChildren = new ArrayList<SelectParseResult>();
    private List<SelectParseResult> selectItemChildren = new ArrayList<SelectParseResult>();

    public List<TableValue> geTableValues() {
      List<TableValue> resultTableValues = new ArrayList<TableValue>();
      resultTableValues.addAll(tableValues.values());
      for (SelectParseResult selectParseResult : whereChildren) {
        resultTableValues.addAll(selectParseResult.geTableValues());
      }
      for (SelectParseResult selectParseResult : fromChildren) {
        resultTableValues.addAll(selectParseResult.geTableValues());
      }
      for (SelectParseResult selectParseResult : selectItemChildren) {
        resultTableValues.addAll(selectParseResult.geTableValues());
      }
      return resultTableValues;
    }

    @Override
    public List<Table> getTables() {
      List<Table> resultTables = new ArrayList<Table>();
      resultTables.addAll(tables);
      for (SelectParseResult selectParseResult : whereChildren) {
        resultTables.addAll(selectParseResult.getTables());
      }
      for (SelectParseResult selectParseResult : fromChildren) {
        resultTables.addAll(selectParseResult.getTables());
      }
      for (SelectParseResult selectParseResult : selectItemChildren) {
        resultTables.addAll(selectParseResult.getTables());
      }
      return resultTables;
    }

    @Override
    public String toString() {
      return "SelectResult{" +
          "tables=" + tables +
          ", whereChildren=" + whereChildren +
          ", fromChildren=" + fromChildren +
          ", selectItemChildren=" + selectItemChildren +
          ", tableValues=" + tableValues +
          '}';
    }
  }

  private class TableValueTreeMap extends TreeMap<String, TableValue> {

    @Override
    public TableValue get(Object key) {
      TableValue tableValue = super.get(key);
      if (tableValue == null) {
        tableValue = new TableValue();
        tableValue.setTableName((String) key);
        super.put((String) key, tableValue);
      }
      return tableValue;
    }

  }

  private class WhereParseResult {
    private List<EqualsColumnValue> equalsColumnValues = new ArrayList<EqualsColumnValue>();
    private List<SelectParseResult> children = new ArrayList<SelectParseResult>();

    @Override
    public String toString() {
      return "WhereResult{" +
          "equalsColumnValues=" + equalsColumnValues +
          ", children=" + children +
          '}';
    }
  }

  /**
   * 找到所有where条件里面使用"="的表达式对
   */
  private class EqualsColumnValue extends ColumnValue {

    private Column column;

    public Column getColumn() {
      return column;
    }

    public void setColumn(Column column) {
      this.column = column;
    }

    @Override
    public String toString() {
      return "EqualsColumnValue{" +
          "column=" + column +
          '}';
    }
  }


}
