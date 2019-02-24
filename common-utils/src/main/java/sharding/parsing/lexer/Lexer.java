package sharding.parsing.lexer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import sharding.parsing.lexer.analyzer.CharType;
import sharding.parsing.lexer.analyzer.Dictionary;
import sharding.parsing.lexer.analyzer.Tokenizer;
import sharding.parsing.lexer.token.Assist;
import sharding.parsing.lexer.token.Token;

/**
 * Created by zhaoheng on 18/10/18.
 * 词法解析器
 */
@RequiredArgsConstructor
public class Lexer {

    @Getter
    private final String input;

    private final Dictionary dictionary;

    private int offset;

    @Getter
    private Token currentToken;

    /**
     * Analyse next token.
     */
    public final void nextToken() {
        skipIgnoredToken();
        if (isVariableBegin()) {
            currentToken = new Tokenizer(input, dictionary, offset).scanVariable();
        } else if (isNCharBegin()) {
            currentToken = new Tokenizer(input, dictionary, ++offset).scanChars();
        } else if (isIdentifierBegin()) {
            currentToken = new Tokenizer(input, dictionary, offset).scanIdentifier();
        } else if (isHexDecimalBegin()) {
            currentToken = new Tokenizer(input, dictionary, offset).scanHexDecimal();
        } else if (isNumberBegin()) {
            currentToken = new Tokenizer(input, dictionary, offset).scanNumber();
        } else if (isSymbolBegin()) {
            currentToken = new Tokenizer(input, dictionary, offset).scanSymbol();
        } else if (isCharsBegin()) {
            currentToken = new Tokenizer(input, dictionary, offset).scanChars();
        } else if (isEnd()) {
            currentToken = new Token(Assist.END, "", offset);
        } else {
            currentToken = new Token(Assist.ERROR, "", offset);
        }
        offset = currentToken.getEndPosition();
    }

    private void skipIgnoredToken() {
        offset = new Tokenizer(input, dictionary, offset).skipWhitespace();
        while (isHintBegin()) {
            offset = new Tokenizer(input, dictionary, offset).skipHint();
            offset = new Tokenizer(input, dictionary, offset).skipWhitespace();
        }
        while (isCommentBegin()) {
            offset = new Tokenizer(input, dictionary, offset).skipComment();
            offset = new Tokenizer(input, dictionary, offset).skipWhitespace();
        }
    }

    protected boolean isHintBegin() {
        return false;
    }

    protected boolean isCommentBegin() {
        char current = getCurrentChar(0);
        char next = getCurrentChar(1);
        return '/' == current && '/' == next || '-' == current && '-' == next || '/' == current && '*' == next;
    }

    protected boolean isVariableBegin() {
        return false;
    }

    protected boolean isSupportNChars() {
        return false;
    }

    private boolean isNCharBegin() {
        return isSupportNChars() && 'N' == getCurrentChar(0) && '\'' == getCurrentChar(1);
    }

    private boolean isIdentifierBegin() {
        return isIdentifierBegin(getCurrentChar(0));
    }

    private boolean isIdentifierBegin(final char ch) {
        return CharType.isAlphabet(ch) || '`' == ch || '_' == ch || '$' == ch;
    }

    private boolean isHexDecimalBegin() {
        return '0' == getCurrentChar(0) && 'x' == getCurrentChar(1);
    }

    private boolean isNumberBegin() {
        return CharType.isDigital(getCurrentChar(0)) || ('.' == getCurrentChar(0) && CharType.isDigital(getCurrentChar(1)) && !isIdentifierBegin(getCurrentChar(-1))
                || ('-' == getCurrentChar(0) && ('.' == getCurrentChar(0) || CharType.isDigital(getCurrentChar(1)))));
    }

    private boolean isSymbolBegin() {
        return CharType.isSymbol(getCurrentChar(0));
    }

    private boolean isCharsBegin() {
        return '\'' == getCurrentChar(0) || '\"' == getCurrentChar(0);
    }

    private boolean isEnd() {
        return offset >= input.length();
    }

    protected final char getCurrentChar(final int offset) {
        return this.offset + offset >= input.length() ? (char) CharType.EOI : input.charAt(this.offset + offset);
    }
}