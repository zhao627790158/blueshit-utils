package sharding.routing.type;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.ToString;

import java.util.*;

@Getter
@ToString
public final class TableUnits {

    private final List<TableUnit> tableUnits = new LinkedList<>();

    /**
     * Get all data source names.
     *
     * @return all data source names
     */
    public Collection<String> getDataSourceNames() {
        Collection<String> result = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        for (TableUnit each : tableUnits) {
            result.add(each.getDataSourceName());
        }
        return result;
    }

    /**
     * Find table unit via data source name and actual table name.
     *
     * @param dataSourceName  data source name
     * @param actualTableName actual table name
     * @return table unit
     */
    public Optional<TableUnit> findTableUnit(final String dataSourceName, final String actualTableName) {
        for (TableUnit each : tableUnits) {
            if (each.getDataSourceName().equalsIgnoreCase(dataSourceName) && each.getActualTableName().equalsIgnoreCase(actualTableName)) {
                return Optional.of(each);
            }
        }
        return Optional.absent();
    }

    /**
     * Get actual tables group via data source name and logic tables' names.
     * <p>
     * Actual tables in same group are belong one logic name.
     * </p>
     *
     * @param dataSourceName  data source name
     * @param logicTableNames logic tables' names
     * @return actual tables group
     */
    public List<Set<String>> getActualTableNameGroups(final String dataSourceName, final Set<String> logicTableNames) {
        List<Set<String>> result = new ArrayList<>();
        for (String logicTableName : logicTableNames) {
            Set<String> actualTableNames = getActualTableNames(dataSourceName, logicTableName);
            if (!actualTableNames.isEmpty()) {
                result.add(actualTableNames);
            }
        }
        return result;
    }

    private Set<String> getActualTableNames(final String dataSourceName, final String logicTableName) {
        Set<String> result = new HashSet<>();
        for (TableUnit each : tableUnits) {
            if (each.getDataSourceName().equalsIgnoreCase(dataSourceName) && each.getLogicTableName().equalsIgnoreCase(logicTableName)) {
                result.add(each.getActualTableName());
            }
        }
        return result;
    }

    /**
     * Get map relationship between data source and logic tables via data sources' names.
     *
     * @param dataSourceNames data sources' names
     * @return map relationship between data source and logic tables
     */
    public Map<String, Set<String>> getDataSourceLogicTablesMap(final Collection<String> dataSourceNames) {
        Map<String, Set<String>> result = new HashMap<>();
        for (String each : dataSourceNames) {
            Set<String> logicTableNames = getLogicTableNames(each);
            if (!logicTableNames.isEmpty()) {
                result.put(each, logicTableNames);
            }
        }
        return result;
    }

    private Set<String> getLogicTableNames(final String dataSourceName) {
        Set<String> result = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        for (TableUnit each : tableUnits) {
            if (each.getDataSourceName().equalsIgnoreCase(dataSourceName)) {
                result.addAll(Lists.transform(tableUnits, new Function<TableUnit, String>() {

                    @Override
                    public String apply(final TableUnit input) {
                        return input.getLogicTableName();
                    }
                }));
            }
        }
        return result;
    }
}