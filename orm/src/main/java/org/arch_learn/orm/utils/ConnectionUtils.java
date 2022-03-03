package org.arch_learn.orm.utils;

import org.arch_learn.orm.io.Resources;
import org.arch_learn.orm.po.Configuration;
import org.arch_learn.orm.sqlSession.DefaultSqlSession;
import org.arch_learn.orm.sqlSession.SqlSessionFactory;
import org.arch_learn.orm.sqlSession.SqlSessionFactoryBuilder;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionUtils {
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    public static Connection getCurrentConnection() {
        Connection connection = threadLocal.get();
        try {
            if (connection == null) {
                InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
                SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
                DefaultSqlSession sqlSession = (DefaultSqlSession) sqlSessionFactory.openSession();
                Configuration configuration = sqlSession.getConfiguration();
                threadLocal.set(configuration.getDataSource().getConnection());
            }
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return threadLocal.get();
    }
}
