package sharding.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ShardingPropertiesConstant {

    /**
     * Enable or Disable to show SQL details.
     * <p>
     * <p>
     * Print SQL details can help developers debug easier.
     * The details includes: logic SQL, parse context and rewrote actual SQL list.
     * Enable this property will log into log topic: {@code Sharding-JDBC-SQL}, log level is {@code INFO}.
     * Default: false
     * </p>
     */
    SQL_SHOW("sql.show", Boolean.FALSE.toString(), boolean.class),

    /**
     * Worker thread max size.
     * <p>
     * <p>
     * Execute SQL Statement and PrepareStatement will use this thread pool.
     * One sharding data source will use a independent thread pool, it does not share thread pool even different data source in same JVM.
     * Default: same with CPU cores.
     * </p>
     */
    EXECUTOR_SIZE("executor.size", String.valueOf(Runtime.getRuntime().availableProcessors()), int.class);

    private final String key;

    private final String defaultValue;

    private final Class<?> type;

    /**
     * Find value via property key.
     *
     * @param key property key
     * @return value enum, return {@code null} if not found
     */
    public static ShardingPropertiesConstant findByKey(final String key) {
        for (ShardingPropertiesConstant each : ShardingPropertiesConstant.values()) {
            if (each.getKey().equals(key)) {
                return each;
            }
        }
        return null;
    }
}
