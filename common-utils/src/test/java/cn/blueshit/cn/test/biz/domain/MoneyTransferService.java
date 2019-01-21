package cn.blueshit.cn.test.biz.domain;

/**
 * Created by zhaoheng on 19/1/9.
 */
public interface MoneyTransferService {

    boolean transfer(String fromAccountId, String toAccountId, double amount);

}
