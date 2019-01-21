package cn.blueshit.cn.test.function;

import java.util.ArrayList;
import java.util.List;

public class AllAndLogicRule<T> implements LogicRule<T> {

    private List<LogicRule<T>> childRules = new ArrayList<>();

    private AllAndLogicRule() {
    }

    public static <T> AllAndLogicRule<T> create() {
        return new AllAndLogicRule<>();
    }

    public boolean mapping(T context) {
        for (LogicRule<T> childRule : childRules) {
            boolean childMapped = childRule.mapping(context);
            if (!childMapped) {
                return false;
            }
        }
        return true;
    }

    public AllAndLogicRule<T> addChild(LogicRule<T> childRule) {
        childRules.add(childRule);
        return this;
    }
}
