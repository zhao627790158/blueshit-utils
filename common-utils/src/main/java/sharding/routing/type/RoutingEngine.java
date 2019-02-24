package sharding.routing.type;

public interface RoutingEngine {
    
    /**
     * Route.
     *
     * @return routing result
     */
    RoutingResult route();
}
