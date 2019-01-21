package cn.blueshit.cn.test.biz.domain;

import lombok.Data;

/**
 * Created by zhaoheng on 19/1/9.
 * 实体
 */
@Data
public class Account {


    private String id;

    private double balance;

    private OverdraftPolicy overdraftPolicy;

    public void debit(double amount) {
        this.overdraftPolicy.preDebit(this, amount);
        this.balance = this.balance - amount;
        this.overdraftPolicy.postDebit(this, amount);
    }

    public void credit(double amount) {
        this.balance = this.balance + amount;
    }


}
