package org.arch_learn.orm.sqlSession;

import org.arch_learn.orm.po.Configuration;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public interface Executor {
    <E> List<E> query(Configuration configuration, String statementId, Object... params) throws SQLException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException, IntrospectionException, InstantiationException, InvocationTargetException;

    void insert(Configuration configuration, String statementId, Object... params) throws Exception;

    void delete(Configuration configuration, String statementId, Object[] args) throws SQLException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException;

    void update(Configuration configuration, String statementId, Object[] args) throws SQLException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException;
}
