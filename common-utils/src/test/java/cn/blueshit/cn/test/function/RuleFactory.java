package cn.blueshit.cn.test.function;

import lombok.Data;

import java.util.function.Function;

/**
 * Created by zhaoheng on 19/1/17.
 */
@Data
public class RuleFactory {


    public static LogicRule<Context> orderType = RuleFactory::isOrder;

    public static LogicRule<Context> tradeType = RuleFactory::isTrade;


    private static boolean isTrade(Context context) {
        return tradeOrderType.apply(context);
    }

    private static boolean isOrder(Context context) {
        return orderTypeFcuntion.apply(context);
    }


    public static Function<Context, Boolean> orderTypeFcuntion = context -> {
        if (context.getCode() == 0) return true;
        return false;
    };

    public static Function<Context, Boolean> tradeOrderType = context -> {
        return !orderTypeFcuntion.apply(context);
    };


}
