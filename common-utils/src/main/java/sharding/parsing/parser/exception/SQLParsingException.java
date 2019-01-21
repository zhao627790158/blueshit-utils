package sharding.parsing.parser.exception;

import sharding.exception.ShardingJdbcException;
import sharding.parsing.lexer.Lexer;
import sharding.parsing.lexer.token.TokenType;

public final class SQLParsingException extends ShardingJdbcException {

    private static final long serialVersionUID = -6408790652103666096L;

    private static final String UNMATCH_MESSAGE = "SQL syntax error, expected token is '%s', actual token is '%s', literals is '%s'.";

    private static final String TOKEN_ERROR_MESSAGE = "SQL syntax error, token is '%s', literals is '%s'.";

    public SQLParsingException(final String message, final Object... args) {
        super(message, args);
    }

    public SQLParsingException(final Lexer lexer, final TokenType expectedTokenType) {
        super(String.format(UNMATCH_MESSAGE, expectedTokenType, lexer.getCurrentToken().getType(), lexer.getCurrentToken().getLiterals()));
    }

}