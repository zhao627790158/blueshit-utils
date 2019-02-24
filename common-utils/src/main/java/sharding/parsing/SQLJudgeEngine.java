package sharding.parsing;

import lombok.RequiredArgsConstructor;
import sharding.parsing.lexer.Lexer;
import sharding.parsing.lexer.analyzer.Dictionary;
import sharding.parsing.lexer.token.Assist;
import sharding.parsing.lexer.token.DefaultKeyword;
import sharding.parsing.lexer.token.Keyword;
import sharding.parsing.lexer.token.TokenType;
import sharding.parsing.parser.exception.SQLParsingException;
import sharding.parsing.parser.sql.SQLStatement;
import sharding.parsing.parser.sql.ddl.DDLStatement;
import sharding.parsing.parser.sql.dml.DMLStatement;
import sharding.parsing.parser.sql.dql.select.SelectStatement;

/**
 * Created by zhaoheng on 18/10/18.
 * sql 校验
 */
@RequiredArgsConstructor
public class SQLJudgeEngine {


    private final String sql;


    public SQLStatement judge() {
        Lexer lexer = new Lexer(sql, new Dictionary());
        lexer.nextToken();
        while (true) {
            //当前token的类型
            TokenType tokenType = lexer.getCurrentToken().getType();
            if (tokenType instanceof Keyword) {
                if (DefaultKeyword.SELECT == tokenType) {
                    return new SelectStatement();
                }
                if (DefaultKeyword.INSERT == tokenType || DefaultKeyword.UPDATE == tokenType || DefaultKeyword.DELETE == tokenType) {
                    return new DMLStatement();
                }
                if (DefaultKeyword.CREATE == tokenType || DefaultKeyword.ALTER == tokenType || DefaultKeyword.DROP == tokenType || DefaultKeyword.TRUNCATE == tokenType) {
                    return new DDLStatement();
                }
            }
            if (tokenType instanceof Assist && Assist.END == tokenType) {
                throw new SQLParsingException("Unsupported SQL statement: [%s]", sql);
            }
            lexer.nextToken();
        }
    }


}
