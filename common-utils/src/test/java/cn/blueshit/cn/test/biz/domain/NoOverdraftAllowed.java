package cn.blueshit.cn.test.biz.domain;


import cn.blueshit.cn.test.biz.domain.Account;
import cn.blueshit.cn.test.biz.domain.OverdraftPolicy;

public class NoOverdraftAllowed implements OverdraftPolicy {
    public void preDebit(Account account, double amount) {
        double newBalance = account.getBalance() - amount;
        if (newBalance < 0) {
            throw new RuntimeException("Insufficient funds");
        }
    }

    public void postDebit(Account account, double amount) {
    }
}