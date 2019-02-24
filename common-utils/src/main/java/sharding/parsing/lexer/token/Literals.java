package sharding.parsing.lexer.token;

/**
 * Created by zhaoheng on 18/10/18.
 * 词法字面量标记
 */
public enum Literals implements TokenType {

    INT,//整数
    FLOAT,//浮点数
    HEX,//十六进制
    CHARS,//字符串
    IDENTIFIER,//词法关键词
    VARIABLE//变量

}
