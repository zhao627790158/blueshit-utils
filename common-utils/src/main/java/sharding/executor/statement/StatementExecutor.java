package sharding.executor.statement;

import lombok.RequiredArgsConstructor;
import sharding.constant.SQLType;
import sharding.executor.ExecutorEngine;

import java.util.Collection;

/**
 * Created by zhaoheng on 18/10/17.
 * 多线程使用的Statement Executor
 */
@RequiredArgsConstructor
public final class StatementExecutor {

    private final ExecutorEngine executorEngine;

    private final SQLType sqlType;

    private final Collection<StatementUnit> statementUnits;

}
