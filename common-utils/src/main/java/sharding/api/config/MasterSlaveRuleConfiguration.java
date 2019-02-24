package sharding.api.config;

import sharding.api.algorithm.masterslave.MasterSlaveLoadBalanceAlgorithmType;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by zhaoheng on 18/10/17.
 * master-slave 规则配置
 */
public class MasterSlaveRuleConfiguration {

    private String name;

    //主库name
    private String masterDataSourceName;

    //从库
    private Collection<String> slaveDataSourceNames = new LinkedList<>();

    private MasterSlaveLoadBalanceAlgorithmType loadBalanceAlgorithmType;

    private String loadBalanceAlgorithmClassName;

}
