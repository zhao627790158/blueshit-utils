package sharding.executor.statement;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import sharding.executor.BaseStatementUnit;
import sharding.routing.SQLExecutionUnit;

import java.sql.Statement;

/**
 * Statement执行单元
 */
@RequiredArgsConstructor
@Getter
public final class StatementUnit implements BaseStatementUnit {

    private final SQLExecutionUnit sqlExecutionUnit;

    private final Statement statement;
}
