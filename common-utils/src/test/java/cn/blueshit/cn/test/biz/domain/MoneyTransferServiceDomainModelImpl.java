package cn.blueshit.cn.test.biz.domain;

import cn.blueshit.cn.test.biz.repository.AccountRepository;
import cn.blueshit.cn.test.biz.repository.BankingTransactionRepository;

/**
 * Created by zhaoheng on 19/1/9.
 */
public class MoneyTransferServiceDomainModelImpl implements MoneyTransferService {

    private AccountRepository accountRepository;
    private BankingTransactionRepository bankingTransactionRepository;

    @Override
    public boolean transfer(String fromAccountId, String toAccountId, double amount) {
        Account fromAccount = accountRepository.findById(fromAccountId);
        Account toAccount = accountRepository.findById(toAccountId);
        fromAccount.debit(amount);
        toAccount.credit(amount);

        return false;
    }
}
