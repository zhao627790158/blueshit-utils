package sharding.hint;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import sharding.api.HintManager;

/**
 * Created by zhaoheng on 18/10/17.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class HintManagerHolder {

    public static final String DB_TABLE_NAME = "DB_TABLE_NAME";

    public static final String DB_COLUMN_NAME = "DB_COLUMN_NAME";

    private static final ThreadLocal<HintManager> HINT_MANAGER_HOLDER = new ThreadLocal<>();





    /**
     * Adjust database sharding only.
     *
     * @return database sharding only or not
     */
    public static boolean isDatabaseShardingOnly() {
        return null != HINT_MANAGER_HOLDER.get() && HINT_MANAGER_HOLDER.get().isDatabaseShardingOnly();
    }

    /**
     * Clear hint manager for current thread-local.
     */
    public static void clear() {
        HINT_MANAGER_HOLDER.remove();
    }
}
