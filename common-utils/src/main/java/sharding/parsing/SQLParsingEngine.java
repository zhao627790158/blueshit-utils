package sharding.parsing;

import lombok.RequiredArgsConstructor;
import sharding.constant.DatabaseType;
import sharding.parsing.lexer.LexerEngine;
import sharding.parsing.lexer.LexerEngineFactory;
import sharding.parsing.parser.sql.SQLParserFactory;
import sharding.parsing.parser.sql.SQLStatement;
import sharding.rule.ShardingRule;

@RequiredArgsConstructor
public final class SQLParsingEngine {

    private final DatabaseType dbType;

    private final String sql;

    private final ShardingRule shardingRule;

    /**
     * Parse SQL.
     *
     * @return parsed SQL statement
     */
    public SQLStatement parse() {
        LexerEngine lexerEngine = LexerEngineFactory.newInstance(dbType, sql);
        lexerEngine.nextToken();
        return SQLParserFactory.newInstance(dbType, lexerEngine.getCurrentToken().getType(), shardingRule, lexerEngine).parse();
    }
}
