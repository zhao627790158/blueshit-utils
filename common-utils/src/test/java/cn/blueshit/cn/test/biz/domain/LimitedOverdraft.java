package cn.blueshit.cn.test.biz.domain;

import lombok.Data;

/**
 * Created by zhaoheng on 19/1/9.
 */
@Data
public class LimitedOverdraft implements OverdraftPolicy {

    private double limit;

    @Override
    public void preDebit(Account account, double amount) {
        double newBalance = account.getBalance() - amount;
        if (newBalance < -limit) {
            throw new RuntimeException("Overdraft limit (of " + limit + ") exceeded: " + newBalance);
        }
    }

    @Override
    public void postDebit(Account account, double amount) {

    }
}
