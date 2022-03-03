package org.arch_learn.orm.utils;

import org.arch_learn.orm.io.Resources;
import org.arch_learn.orm.sqlSession.SqlSession;
import org.arch_learn.orm.sqlSession.SqlSessionFactory;
import org.arch_learn.orm.sqlSession.SqlSessionFactoryBuilder;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.InputStream;

public class SessionUtils {
    public static SqlSession getDefaultSqlSession() throws PropertyVetoException, DocumentException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        return sqlSession;
    }
}
