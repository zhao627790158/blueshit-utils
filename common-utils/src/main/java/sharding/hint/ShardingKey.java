package sharding.hint;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
public final class ShardingKey {

    /**
     * Logic table name.
     */
    private final String logicTable;

    /**
     * Sharding column name.
     */
    @Getter
    private final String shardingColumn;

    public ShardingKey(final String logicTable, final String shardingColumn) {
        this.logicTable = logicTable.toLowerCase();
        this.shardingColumn = shardingColumn.toLowerCase();
    }
}