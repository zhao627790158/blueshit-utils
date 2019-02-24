package sharding.parsing.parser.sql.dml;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import sharding.constant.SQLType;
import sharding.parsing.parser.sql.AbstractSQLStatement;

@Getter
@Setter
@ToString(callSuper = true)
public class DMLStatement extends AbstractSQLStatement {

    public DMLStatement() {
        super(SQLType.DML);
    }
}
