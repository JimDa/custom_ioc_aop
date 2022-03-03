package org.arch_learn.ioc_aop_test.service.impl;


import org.arch_learn.ioc_aop.annos.Autowired;
import org.arch_learn.ioc_aop.annos.Service;
import org.arch_learn.ioc_aop.annos.Transactional;
import org.arch_learn.ioc_aop_test.mapper.AccountMapper;
import org.arch_learn.ioc_aop_test.po.Account;
import org.arch_learn.ioc_aop_test.service.TransferService;

@Service(value = "transferService")
public class TransferServiceImpl implements TransferService {

    @Autowired(value = "accountMapper")
    private AccountMapper accountMapper;


    @Transactional
    public void transfer(String fromCardNo, String toCardNo, int money) throws Exception {
        Account fromAccount = new Account();
        fromAccount.setCard_no(fromCardNo);
        Account toAccount = new Account();
        toAccount.setCard_no(toCardNo);
        fromAccount = accountMapper.queryAccountByCardNo(fromAccount);
        toAccount = accountMapper.queryAccountByCardNo(toAccount);

        fromAccount.setMoney(fromAccount.getMoney() - money);
        toAccount.setMoney(toAccount.getMoney() + money);

        accountMapper.updateAccountByCardNo(toAccount);
        int i = 1 / 0;
        accountMapper.updateAccountByCardNo(fromAccount);
    }
}
