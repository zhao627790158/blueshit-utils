package cn.blueshit.cn.test.sql;

/**
 * Created by wangjinsi on 2015/12/11.
 */
public enum SqlType {

  INSERT, UPDATE, DELETE, SELECT;

  public static SqlType getSqlType(String sql) {
    sql = sql.trim().toLowerCase();
    if (sql.startsWith("insert")) {
      return INSERT;
    } else if (sql.startsWith("update")) {
      return UPDATE;
    } else if (sql.startsWith("delete")) {
      return DELETE;
    } else if (sql.startsWith("select")) {
      return SELECT;
    }
    throw new UnsupportedOperationException(
        "支持的SQL类型：insert/update/delete/select,sql=" + sql);
  }

}
