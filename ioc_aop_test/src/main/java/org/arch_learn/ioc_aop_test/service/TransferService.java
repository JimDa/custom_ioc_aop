package org.arch_learn.ioc_aop_test.service;

import org.arch_learn.ioc_aop.annos.Transactional;

/**
 * @author 应癫
 */
public interface TransferService {

    @Transactional
    void transfer(String fromCardNo, String toCardNo, int money) throws Exception;
}
