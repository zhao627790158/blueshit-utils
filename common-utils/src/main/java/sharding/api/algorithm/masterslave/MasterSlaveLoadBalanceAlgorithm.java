package sharding.api.algorithm.masterslave;

import java.util.List;

public interface MasterSlaveLoadBalanceAlgorithm {

    /**
     * Get data source.
     *
     * @param name                 master-slave logic data source name
     * @param masterDataSourceName name of master data sources
     * @param slaveDataSourceNames names of slave data sources
     * @return name of selected data source
     */
    String getDataSource(String name, String masterDataSourceName, List<String> slaveDataSourceNames);
}