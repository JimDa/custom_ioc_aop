package org.arch_learn.orm.sqlSession;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public interface SqlSession {
    <T> T getMapper(Class<?> mapperClass);

    //查询多个
    <T> List<T> selectList(String statementId, Object... params) throws SQLException, IntrospectionException, NoSuchFieldException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException;

    //查询单个
    <T> T selectOne(String statementId, Object... params) throws SQLException, IntrospectionException, NoSuchFieldException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException;

    //增加
    void insertOne(String statementId, Object... params);

    //删除
    void deleteByPrimaryKey(String statementId, Object... params);

    //修改
    void updateByPrimaryKey(String statementId, Object... params);

    //查询所有
    <E> List<E> selectAll(String statementId, Object... params) throws SQLException, IntrospectionException, NoSuchFieldException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException;

    //根据id查询单个
    <E> E selectByPrimaryKey(String statementId, Object... params) throws SQLException, IntrospectionException, NoSuchFieldException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException;

    //根据条件查询多个
    <E> List<E> selectByCondition(String statementId, Object... params) throws SQLException, IntrospectionException, NoSuchFieldException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException;
}
