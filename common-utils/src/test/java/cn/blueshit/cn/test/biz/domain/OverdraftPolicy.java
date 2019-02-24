package cn.blueshit.cn.test.biz.domain;

import cn.blueshit.cn.test.biz.domain.Account;

/**
 * Created by zhaoheng on 19/1/9.
 */
public interface OverdraftPolicy {


    void preDebit(Account account, double amount);


    void postDebit(Account account, double amount);

}
