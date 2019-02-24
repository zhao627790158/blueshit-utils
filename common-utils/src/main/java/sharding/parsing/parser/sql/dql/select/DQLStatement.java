package sharding.parsing.parser.sql.dql.select;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import sharding.constant.SQLType;
import sharding.parsing.parser.sql.AbstractSQLStatement;

@Getter
@Setter
@ToString(callSuper = true)
public class DQLStatement extends AbstractSQLStatement {

    public DQLStatement() {
        super(SQLType.DQL);
    }
}