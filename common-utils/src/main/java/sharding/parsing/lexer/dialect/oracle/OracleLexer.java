package sharding.parsing.lexer.dialect.oracle;

import sharding.parsing.lexer.Lexer;
import sharding.parsing.lexer.analyzer.Dictionary;

public final class OracleLexer extends Lexer {

    private static Dictionary dictionary = new Dictionary(OracleKeyword.values());

    public OracleLexer(final String input) {
        super(input, dictionary);
    }

    @Override
    protected boolean isHintBegin() {
        return '/' == getCurrentChar(0) && '*' == getCurrentChar(1) && '+' == getCurrentChar(2);
    }
}
