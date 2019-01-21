package sharding.parsing.lexer.dialect.myql;

import sharding.parsing.lexer.Lexer;
import sharding.parsing.lexer.analyzer.Dictionary;

public final class MySQLLexer extends Lexer {

    private static Dictionary dictionary = new Dictionary(MySQLKeyword.values());

    public MySQLLexer(final String input) {
        super(input, dictionary);
    }

    @Override
    protected boolean isHintBegin() {
        return '/' == getCurrentChar(0) && '*' == getCurrentChar(1) && '!' == getCurrentChar(2);
    }

    @Override
    protected boolean isCommentBegin() {
        return '#' == getCurrentChar(0) || super.isCommentBegin();
    }

    @Override
    protected boolean isVariableBegin() {
        return '@' == getCurrentChar(0);
    }
}
