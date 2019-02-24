package sharding.parsing.parser.sql.ddl;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import sharding.constant.SQLType;
import sharding.parsing.parser.sql.AbstractSQLStatement;

@Getter
@Setter
@ToString(callSuper = true)
public final class DDLStatement extends AbstractSQLStatement {

    public DDLStatement() {
        super(SQLType.DDL);
    }
}
