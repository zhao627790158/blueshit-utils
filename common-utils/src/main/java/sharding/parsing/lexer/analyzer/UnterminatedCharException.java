package sharding.parsing.lexer.analyzer;

import sharding.exception.ShardingJdbcException;

public final class UnterminatedCharException extends ShardingJdbcException {

    private static final long serialVersionUID = 8575890835166900925L;

    private static final String MESSAGE = "Illegal input, unterminated '%s'.";

    public UnterminatedCharException(final char terminatedChar) {
        super(String.format(MESSAGE, terminatedChar));
    }

    public UnterminatedCharException(final String terminatedChar) {
        super(String.format(MESSAGE, terminatedChar));
    }
}