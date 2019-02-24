package sharding.parsing.parser.sql;

import sharding.constant.SQLType;
import sharding.parsing.parser.context.condition.Conditions;
import sharding.parsing.parser.context.table.Tables;
import sharding.parsing.parser.token.SQLToken;

import java.util.List;

public interface SQLStatement {

    /**
     * Get SQL type.
     *
     * @return SQL type
     */
    SQLType getType();

    /**
     * Get tables.
     *
     * @return tables
     */
    Tables getTables();

    /**
     * Get conditions.
     *
     * @return conditions
     */
    Conditions getConditions();

    /**
     * Get SQL Tokens.
     *
     * @return SQL Tokens
     */
    List<SQLToken> getSqlTokens();

    /**
     * Get index of parameters.
     *
     * @return index of parameters
     */
    int getParametersIndex();

    /**
     * Set index of parameters.
     *
     * @param parametersIndex index of parameters
     */
    void setParametersIndex(int parametersIndex);

    /**
     * 增加索引偏移量.
     *
     * @return 增加后的索引偏移量
     */
    int increaseParametersIndex();
}
