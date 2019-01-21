package sharding.parsing.parser.exception;

import sharding.exception.ShardingJdbcException;
import sharding.parsing.lexer.token.TokenType;

public final class SQLParsingUnsupportedException extends ShardingJdbcException {

    private static final long serialVersionUID = -4968036951399076811L;

    private static final String MESSAGE = "Not supported token '%s'.";

    public SQLParsingUnsupportedException(final TokenType tokenType) {
        super(String.format(MESSAGE, tokenType.toString()));
    }
}
