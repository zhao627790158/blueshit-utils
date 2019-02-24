package sharding.routing;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import sharding.parsing.parser.sql.SQLStatement;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Getter
public final class SQLRouteResult {

    private final SQLStatement sqlStatement;

    private final Set<SQLExecutionUnit> executionUnits = new LinkedHashSet<>();

    private final List<Number> generatedKeys = new LinkedList<>();
}
