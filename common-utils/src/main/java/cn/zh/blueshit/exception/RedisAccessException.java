package cn.zh.blueshit.exception;

/**
 * Created by zhaoheng on 2016/5/23.
 */
public class RedisAccessException extends Exception {

    public RedisAccessException(String msg) {
        super(msg);
    }

    public RedisAccessException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public RedisAccessException(Throwable cause) {
        super(cause);
    }
}
