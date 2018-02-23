package cn.blueshit.cn.test.sql;

import com.google.common.base.CharMatcher;
import org.junit.Test;

/**
 * Created by wangjinsi on 2015/11/26.
 */
public abstract class SqlUtils {

  public static String getSqlWithParameterIndex(String sqlText) {
    StringBuilder sqlTextBuilder = new StringBuilder(sqlText);
    int parameterIndex = 0;
    int searchStartIndex = -1;
    while ((searchStartIndex = sqlTextBuilder.indexOf("?", searchStartIndex)) > -1) {
      sqlTextBuilder.insert(searchStartIndex + 1, ++parameterIndex);
      searchStartIndex += new StringBuilder("?").append(parameterIndex).length();
    }
    return sqlTextBuilder.toString();
  }


}
