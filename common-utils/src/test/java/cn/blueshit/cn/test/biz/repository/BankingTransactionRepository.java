package cn.blueshit.cn.test.biz.repository;

import cn.blueshit.cn.test.biz.domain.Account;

/**
 * Created by zhaoheng on 19/1/9.
 */
public interface BankingTransactionRepository {
    Account findById(String accountId);
}
