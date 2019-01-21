package sharding.rule;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * Created by zhaoheng on 18/10/17.
 * 绑定表规则配置
 * <p>绑定表中不同的表使用相同的分片规则,使用一个可以推导出实际表和数据源名称</p>
 */
@RequiredArgsConstructor
@Getter
public class BindingTableRule {

    private final List<TableRule> tableRules;

}
