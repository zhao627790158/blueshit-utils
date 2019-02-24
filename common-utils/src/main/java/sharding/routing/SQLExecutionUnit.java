package sharding.routing;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public final class SQLExecutionUnit {

    private final String dataSource;

    private final String sql;
}