package cn.blueshit.cn.test.function;

/**
 * Created by zhaoheng on 19/1/17.
 */
public class RuleTest {


    public static void main(String[] args) {
        Context context = new Context();
        AllOrLogicRule<Context> contextAllAndLogicRule = orRule(RuleFactory.orderType, orRule(RuleFactory.tradeType));
        boolean mapping = contextAllAndLogicRule.mapping(context);
        System.out.println(mapping);
    }


    @SafeVarargs
    private static AllAndLogicRule<Context> andRule(LogicRule<Context>... rules) {
        AllAndLogicRule<Context> all = AllAndLogicRule.create();
        for (LogicRule<Context> logicRule : rules) {
            all.addChild(logicRule);
        }
        return all;
    }

    @SafeVarargs
    private static AllOrLogicRule<Context> orRule(LogicRule<Context>... rules) {
        AllOrLogicRule<Context> all = AllOrLogicRule.create();
        for (LogicRule<Context> logicRule : rules) {
            all.addChild(logicRule);
        }
        return all;
    }
}
