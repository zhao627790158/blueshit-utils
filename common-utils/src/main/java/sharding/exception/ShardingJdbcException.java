package sharding.exception;

/**
 * Created by zhaoheng on 18/10/17.
 */
public class ShardingJdbcException extends RuntimeException {

    public ShardingJdbcException(final String errorMessage, final Object... args) {
        super(String.format(errorMessage, args));
    }

    public ShardingJdbcException(String message) {
        super(message);
    }

    public ShardingJdbcException(String message, Throwable cause) {
        super(message, cause);
    }

    public ShardingJdbcException(Throwable cause) {
        super(cause);
    }
}
