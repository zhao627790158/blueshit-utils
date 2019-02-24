package sharding.api.algorithm.masterslave;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum MasterSlaveLoadBalanceAlgorithmType {

    ROUND_ROBIN(new RoundRobinMasterSlaveLoadBalanceAlgorithm()),
    RANDOM(new RandomMasterSlaveLoadBalanceAlgorithm());

    private final MasterSlaveLoadBalanceAlgorithm algorithm;

    /**
     * Get default master-slave database load-balance algorithm type.
     *
     * @return default master-slave database load-balance algorithm type
     */
    public static MasterSlaveLoadBalanceAlgorithmType getDefaultAlgorithmType() {
        return ROUND_ROBIN;
    }
}
