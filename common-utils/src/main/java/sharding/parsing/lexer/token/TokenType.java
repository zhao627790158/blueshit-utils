package sharding.parsing.lexer.token;

/**
 * 词法标记类型
 * DefaultKeyword ：词法关键词
 * Literals ：词法字面量标记
 * Symbol ：词法符号标记
 * Assist ：词法辅助标记
 * SELECT i.* FROM t_order o JOIN t_order_item i ON o.order_id=i.order_id WHERE o.user_id=? AND o.order_id=?
 * select -> 词法关键词
 * i-> 词法字面量标记Literals
 * .->词法符号标记Symbol
 * * ->词法符号标记Symbol
 *
 */
public interface TokenType {
}