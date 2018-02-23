package cn.blueshit.cn.test.sql;

/**
 * Created by wangjinsi on 2015/11/30.
 */

public class ColumnValue implements Cloneable {

  private String columnName;

  private String columnValue;

  public ColumnValue() {
  }

  public ColumnValue(String columnName, String columnValue) {
    this.columnName = columnName;
    this.columnValue = columnValue;
  }

  public String getColumnName() {
    return columnName;
  }

  public void setColumnName(String columnName) {
    this.columnName = columnName;
  }

  public String getColumnValue() {
    return columnValue;
  }

  public void setColumnValue(String columnValue) {
    this.columnValue = columnValue;
  }

  @Override
  public String toString() {
    return "TableColumnValue{" +
        "columnName='" + columnName + '\'' +
        ", columnValue='" + columnValue + '\'' +
        '}';
  }

  public ColumnValue clone() {
    return new ColumnValue(this.columnName, this.columnValue);
  }
}