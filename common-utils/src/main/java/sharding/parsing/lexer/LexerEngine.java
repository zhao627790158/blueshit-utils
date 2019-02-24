package sharding.parsing.lexer;

import com.google.common.collect.Sets;
import lombok.RequiredArgsConstructor;
import sharding.parsing.lexer.token.Assist;
import sharding.parsing.lexer.token.Symbol;
import sharding.parsing.lexer.token.Token;
import sharding.parsing.lexer.token.TokenType;
import sharding.parsing.parser.exception.SQLParsingException;
import sharding.parsing.parser.exception.SQLParsingUnsupportedException;
import sharding.parsing.parser.sql.SQLStatement;

import java.util.Set;

@RequiredArgsConstructor
public final class LexerEngine {

    private final Lexer lexer;

    /**
     * Get input string.
     *
     * @return inputted string
     */
    public String getInput() {
        return lexer.getInput();
    }

    /**
     * Analyse next token.
     */
    public void nextToken() {
        lexer.nextToken();
    }

    /**
     * Get current token.
     *
     * @return current token
     */
    public Token getCurrentToken() {
        return lexer.getCurrentToken();
    }

    /**
     * skip all tokens that inside parentheses.
     *
     * @param sqlStatement SQL statement
     * @return skipped string
     */
    public String skipParentheses(final SQLStatement sqlStatement) {
        StringBuilder result = new StringBuilder("");
        int count = 0;
        if (Symbol.LEFT_PAREN == lexer.getCurrentToken().getType()) {
            final int beginPosition = lexer.getCurrentToken().getEndPosition();
            result.append(Symbol.LEFT_PAREN.getLiterals());
            lexer.nextToken();
            while (true) {
                if (equalAny(Symbol.QUESTION)) {
                    sqlStatement.increaseParametersIndex();
                }
                if (Assist.END == lexer.getCurrentToken().getType() || (Symbol.RIGHT_PAREN == lexer.getCurrentToken().getType() && 0 == count)) {
                    break;
                }
                if (Symbol.LEFT_PAREN == lexer.getCurrentToken().getType()) {
                    count++;
                } else if (Symbol.RIGHT_PAREN == lexer.getCurrentToken().getType()) {
                    count--;
                }
                lexer.nextToken();
            }
            result.append(lexer.getInput().substring(beginPosition, lexer.getCurrentToken().getEndPosition()));
            lexer.nextToken();
        }
        return result.toString();
    }

    /**
     * Assert current token type should equals input token and go to next token type.
     *
     * @param tokenType token type
     */
    public void accept(final TokenType tokenType) {
        if (lexer.getCurrentToken().getType() != tokenType) {
            throw new SQLParsingException(lexer, tokenType);
        }
        lexer.nextToken();
    }

    /**
     * Adjust current token equals one of input tokens or not.
     *
     * @param tokenTypes to be adjusted token types
     * @return current token equals one of input tokens or not
     */
    public boolean equalAny(final TokenType... tokenTypes) {
        for (TokenType each : tokenTypes) {
            if (each == lexer.getCurrentToken().getType()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Skip current token if equals one of input tokens.
     *
     * @param tokenTypes to be adjusted token types
     * @return skipped current token or not
     */
    public boolean skipIfEqual(final TokenType... tokenTypes) {
        if (equalAny(tokenTypes)) {
            lexer.nextToken();
            return true;
        }
        return false;
    }

    /**
     * Skip all input tokens.
     *
     * @param tokenTypes to be skipped token types
     */
    public void skipAll(final TokenType... tokenTypes) {
        Set<TokenType> tokenTypeSet = Sets.newHashSet(tokenTypes);
        while (tokenTypeSet.contains(lexer.getCurrentToken().getType())) {
            lexer.nextToken();
        }
    }

    /**
     * Skip until one of input tokens.
     *
     * @param tokenTypes to be skipped untiled token types
     */
    public void skipUntil(final TokenType... tokenTypes) {
        Set<TokenType> tokenTypeSet = Sets.newHashSet(tokenTypes);
        tokenTypeSet.add(Assist.END);
        while (!tokenTypeSet.contains(lexer.getCurrentToken().getType())) {
            lexer.nextToken();
        }
    }

    /**
     * Throw unsupported exception if current token equals one of input tokens.
     *
     * @param tokenTypes to be adjusted token types
     */
    public void unsupportedIfEqual(final TokenType... tokenTypes) {
        if (equalAny(tokenTypes)) {
            throw new SQLParsingUnsupportedException(lexer.getCurrentToken().getType());
        }
    }

    /**
     * Throw unsupported exception if current token not equals one of input tokens.
     *
     * @param tokenTypes to be adjusted token types
     */
    public void unsupportedIfNotSkip(final TokenType... tokenTypes) {
        if (!skipIfEqual(tokenTypes)) {
            throw new SQLParsingUnsupportedException(lexer.getCurrentToken().getType());
        }
    }
}
