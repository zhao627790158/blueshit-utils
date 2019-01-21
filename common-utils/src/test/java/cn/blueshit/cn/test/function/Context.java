package cn.blueshit.cn.test.function;

import lombok.Data;

/**
 * 统一上下文
 * Created by zhaoheng on 19/1/17.
 */
@Data
public class Context {

    private int code;

    private String line;

    private String msg;

    private Object demo;

}
