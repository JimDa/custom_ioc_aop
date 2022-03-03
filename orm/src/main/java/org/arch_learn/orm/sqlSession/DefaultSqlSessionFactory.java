package org.arch_learn.orm.sqlSession;

import org.arch_learn.orm.po.Configuration;

public class DefaultSqlSessionFactory implements SqlSessionFactory {
    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}
