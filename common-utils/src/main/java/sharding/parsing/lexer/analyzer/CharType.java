package sharding.parsing.lexer.analyzer;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CharType {

    /**
     * End of input.
     */
    public static final byte EOI = 0x1A;

    /**
     * Adjust is whitespace or not.
     *
     * @param ch to be adjusted char
     * @return is whitespace or not
     */
    public static boolean isWhitespace(final char ch) {
        return ch <= 32 && EOI != ch || 160 == ch || ch >= 0x7F && ch <= 0xA0;
    }

    /**
     * Adjust is end of input or not.
     * 0x1A在ASCII码中代表EOF，在过去，ASCII码EOF曾经在unix/linux中被作为文件结束符使用
     *
     * @param ch to be adjusted char
     * @return is end of input or not
     */
    public static boolean isEndOfInput(final char ch) {
        return ch == 0x1A;
    }

    /**
     * Adjust is alphabet or not.
     *
     * @param ch to be adjusted char
     * @return is alphabet or not
     */
    public static boolean isAlphabet(final char ch) {
        return ch >= 'A' && ch <= 'Z' || ch >= 'a' && ch <= 'z';
    }

    /**
     * Adjust is digital or not.
     *
     * @param ch to be adjusted char
     * @return is alphabet or not
     */
    public static boolean isDigital(final char ch) {
        return ch >= '0' && ch <= '9';
    }

    /**
     * Adjust is symbol or not.
     *
     * @param ch to be adjusted char
     * @return is symbol or not
     */
    public static boolean isSymbol(final char ch) {
        return '(' == ch || ')' == ch || '[' == ch || ']' == ch || '{' == ch || '}' == ch || '+' == ch || '-' == ch || '*' == ch || '/' == ch || '%' == ch || '^' == ch || '=' == ch
                || '>' == ch || '<' == ch || '~' == ch || '!' == ch || '?' == ch || '&' == ch || '|' == ch || '.' == ch || ':' == ch || '#' == ch || ',' == ch || ';' == ch;
    }
}