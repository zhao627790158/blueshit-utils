package cn.zh.blueshit.exception;

/**
 * Created by zhaoheng on 2016/3/27.
 */
public class OperateException extends Exception {
    private String code;

    public OperateException() {
        super();
    }

    public String getCode() {
        return code;
    }

    public OperateException(String message) {
        super(message);
    }

    public OperateException(Throwable cause) {
        super(cause);
    }

    public OperateException(String code, String message) {
        super(message);
        this.code = code;
    }

    public OperateException(String message, Throwable cause) {
        super(message, cause);
    }

    public OperateException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

}
