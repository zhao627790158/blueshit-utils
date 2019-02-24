package sharding.routing.type.all;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sharding.routing.type.RoutingEngine;
import sharding.routing.type.RoutingResult;
import sharding.routing.type.TableUnit;

import javax.sql.DataSource;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
public final class DatabaseAllRoutingEngine implements RoutingEngine {
    
    private final Map<String, DataSource> dataSourceMap;
    
    @Override
    public RoutingResult route() {
        RoutingResult result = new RoutingResult();
        for (String each : dataSourceMap.keySet()) {
            result.getTableUnits().getTableUnits().add(new TableUnit(each, "", ""));
        }
        return result;
    }
}
