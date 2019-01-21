package sharding.routing.type;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public final class TableUnit {

    //数据源名称
    private final String dataSourceName;

    //逻辑表名
    private final String logicTableName;

    //真实表名
    private final String actualTableName;



}
