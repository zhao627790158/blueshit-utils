package cn.blueshit.cn.test.sql;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wangjinsi on 2015/11/30.
 */

public class TableValue implements Cloneable {

  private String tableName;

  private List<ColumnValue> columnValues = new ArrayList<ColumnValue>();

  public TableValue() {

  }

  public TableValue(String tableName, List<ColumnValue> columnValues) {
    this.tableName = tableName;
    this.columnValues = columnValues;
  }

  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public List<ColumnValue> getColumnValues() {
    return columnValues;
  }

  public void setColumnValues(List<ColumnValue> columnValues) {
    this.columnValues = columnValues;
  }

  @Override
  public TableValue clone() {
    List<ColumnValue> tempColumnValues = null;
    if (this.columnValues != null) {
      tempColumnValues = new ArrayList<ColumnValue>(this.columnValues.size());
      for (ColumnValue columnValue : this.columnValues) {
        tempColumnValues.add(columnValue.clone());
      }
    }
    return new TableValue(this.tableName, tempColumnValues);
  }

  @Override
  public String toString() {
    return "TableValue{" +
        "tableName='" + tableName + '\'' +
        ", columnValues=" + (columnValues != null ? Arrays.toString(columnValues.toArray()) : "null") +
        '}';
  }
}