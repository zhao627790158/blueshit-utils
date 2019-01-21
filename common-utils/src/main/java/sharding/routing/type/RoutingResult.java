package sharding.routing.type;

import lombok.Getter;

@Getter
public class RoutingResult {

    private final TableUnits tableUnits = new TableUnits();

    /**
     * Adjust is route for single database and table only or not.
     *
     * @return is route for single database and table only or not
     */
    public boolean isSingleRouting() {
        return 1 == tableUnits.getTableUnits().size();
    }

}
