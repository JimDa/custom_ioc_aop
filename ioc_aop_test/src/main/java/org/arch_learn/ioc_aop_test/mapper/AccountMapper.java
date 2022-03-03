package org.arch_learn.ioc_aop_test.mapper;

import org.arch_learn.ioc_aop.annos.Mapper;
import org.arch_learn.ioc_aop_test.po.Account;

@Mapper(value = "accountMapper")
public interface AccountMapper {
    Account queryAccountByCardNo(Account account);

    void updateAccountByCardNo(Account account);
}
