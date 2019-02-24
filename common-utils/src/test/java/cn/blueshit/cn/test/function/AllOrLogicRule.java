package cn.blueshit.cn.test.function;

import java.util.ArrayList;
import java.util.List;

public class AllOrLogicRule<T> implements LogicRule<T> {

    private List<LogicRule<T>> childRules = new ArrayList<>();

    private AllOrLogicRule() {
    }

    public static <T> AllOrLogicRule<T> create() {
        return new AllOrLogicRule<>();
    }

    public boolean mapping(T context) {
        for (LogicRule<T> childRule : childRules) {
            boolean childMapped = childRule.mapping(context);
            if (childMapped) {
                return true;
            }
        }
        return false;
    }

    public AllOrLogicRule<T> addChild(LogicRule<T> childRule) {
        childRules.add(childRule);
        return this;
    }
}