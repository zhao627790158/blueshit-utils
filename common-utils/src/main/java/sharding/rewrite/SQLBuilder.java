package sharding.rewrite;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class SQLBuilder {

    /**
     * 段集合
     */
    private final List<Object> segments;


    /**
     * 当前段
     */
    private StringBuilder currentSegment;

    /**
     * Constructs a empty SQL builder.
     */
    public SQLBuilder() {
        segments = new LinkedList<>();
        currentSegment = new StringBuilder();
        segments.add(currentSegment);
    }

    /**
     * Append literals.
     * 追加字面量
     *
     * @param literals literals for SQL
     */
    public void appendLiterals(final String literals) {
        currentSegment.append(literals);
    }

    /**
     * Append table token.
     *
     * @param tableName table name
     */
    public void appendTable(final String tableName) {
        segments.add(new TableToken(tableName));
        currentSegment = new StringBuilder();
        segments.add(currentSegment);
    }

    /**
     * Convert to SQL string.
     *
     * @param tableTokens table tokens
     * @return SQL string
     */
    public String toSQL(final Map<String, String> tableTokens) {
        StringBuilder result = new StringBuilder();
        for (Object each : segments) {
            if (each instanceof TableToken && tableTokens.containsKey(((TableToken) each).tableName)) {
                result.append(tableTokens.get(((TableToken) each).tableName));
            } else {
                result.append(each);
            }
        }
        return result.toString();
    }

    @RequiredArgsConstructor
    private class TableToken {

        private final String tableName;

        @Override
        public String toString() {
            return tableName;
        }
    }
}