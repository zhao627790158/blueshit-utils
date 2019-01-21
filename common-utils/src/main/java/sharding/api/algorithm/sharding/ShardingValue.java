package sharding.api.algorithm.sharding;

/**
 * Created by zhaoheng on 18/10/17.
 * 分片信息接口
 */
public interface ShardingValue {


    /**
     * 获取逻辑表名
     * @return
     */
    String getLogicTableName();


    /**
     * 获取列名称
     * @return
     */
    String getColumnName();


}
