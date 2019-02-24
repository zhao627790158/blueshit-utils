package sharding.parsing.lexer;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import sharding.constant.DatabaseType;
import sharding.parsing.lexer.dialect.myql.MySQLLexer;
import sharding.parsing.lexer.dialect.oracle.OracleLexer;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LexerEngineFactory {

    /**
     * Create lexical analysis engine instance.
     *
     * @param dbType database type
     * @param sql    SQL
     * @return lexical analysis engine instance
     */
    public static LexerEngine newInstance(final DatabaseType dbType, final String sql) {
        switch (dbType) {
            case H2:
            case MySQL:
                return new LexerEngine(new MySQLLexer(sql));
            case Oracle:
                return new LexerEngine(new OracleLexer(sql));
           /* case SQLServer:
                return new LexerEngine(new SQLServerLexer(sql));
            case PostgreSQL:
                return new LexerEngine(new PostgreSQLLexer(sql));*/
            default:
                throw new UnsupportedOperationException(String.format("Cannot support database [%s].", dbType));
        }
    }
}
