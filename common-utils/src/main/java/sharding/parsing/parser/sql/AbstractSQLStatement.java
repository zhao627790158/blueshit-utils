package sharding.parsing.parser.sql;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import sharding.constant.SQLType;
import sharding.parsing.parser.context.condition.Conditions;
import sharding.parsing.parser.context.table.Tables;
import sharding.parsing.parser.token.SQLToken;

import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
public abstract class AbstractSQLStatement implements SQLStatement {

    private final SQLType type;

    private final Tables tables = new Tables();

    private final Conditions conditions = new Conditions();

    private final List<SQLToken> sqlTokens = new LinkedList<>();

    private int parametersIndex;

    @Override
    public final SQLType getType() {
        return type;
    }

    @Override
    public int increaseParametersIndex() {
        return ++parametersIndex;
    }
}
