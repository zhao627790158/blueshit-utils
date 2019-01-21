package sharding.parsing.parser.sql;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import sharding.constant.DatabaseType;
import sharding.parsing.lexer.LexerEngine;
import sharding.parsing.lexer.token.DefaultKeyword;
import sharding.parsing.lexer.token.TokenType;
import sharding.parsing.parser.exception.SQLParsingUnsupportedException;
import sharding.rule.ShardingRule;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SQLParserFactory {

    /**
     * Create SQL parser.
     *
     * @param dbType       database type
     * @param tokenType    token type
     * @param shardingRule databases and tables sharding rule
     * @param lexerEngine  lexical analysis engine
     * @return SQL parser
     */
    public static SQLParser newInstance(final DatabaseType dbType, final TokenType tokenType, final ShardingRule shardingRule, final LexerEngine lexerEngine) {
        if (!(tokenType instanceof DefaultKeyword)) {
            throw new SQLParsingUnsupportedException(tokenType);
        }
        switch ((DefaultKeyword) tokenType) {
           /* case SELECT:
                return SelectParserFactory.newInstance(dbType, shardingRule, lexerEngine);
            case INSERT:
                return InsertParserFactory.newInstance(dbType, shardingRule, lexerEngine);
            case UPDATE:
                return UpdateParserFactory.newInstance(dbType, shardingRule, lexerEngine);
            case DELETE:
                return DeleteParserFactory.newInstance(dbType, shardingRule, lexerEngine);
            case CREATE:
                return CreateParserFactory.newInstance(dbType, shardingRule, lexerEngine);
            case ALTER:
                return AlterParserFactory.newInstance(dbType, shardingRule, lexerEngine);
            case DROP:
                return DropParserFactory.newInstance(dbType, shardingRule, lexerEngine);
            case TRUNCATE:
                return TruncateParserFactory.newInstance(dbType, shardingRule, lexerEngine);*/
            default:
                throw new SQLParsingUnsupportedException(lexerEngine.getCurrentToken().getType());
        }
    }
}
