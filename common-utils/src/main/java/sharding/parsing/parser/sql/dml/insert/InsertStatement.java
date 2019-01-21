package sharding.parsing.parser.sql.dml.insert;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.mybatis.generator.config.GeneratedKey;
import sharding.parsing.parser.context.condition.Column;
import sharding.parsing.parser.context.condition.Conditions;
import sharding.parsing.parser.sql.dml.DMLStatement;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@ToString
public final class InsertStatement extends DMLStatement {

    private final Collection<Column> columns = new LinkedList<>();

    private final List<Conditions> multipleConditions = new LinkedList<>();

    private int columnsListLastPosition;

    private int generateKeyColumnIndex = -1;

    private int afterValuesPosition;

    private int valuesListLastPosition;

    private GeneratedKey generatedKey;

}