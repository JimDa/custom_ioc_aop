package org.arch_learn.orm.sqlSession;

import org.arch_learn.orm.po.Configuration;
import org.arch_learn.orm.po.MappedStatement;

import java.beans.IntrospectionException;
import java.lang.reflect.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class DefaultSqlSession implements SqlSession {
    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <T> T getMapper(Class<?> mapperClass) {
        //使用JDK动态代理来为dao接口生成代理对象，并返回
        Object proxyInstance = Proxy.newProxyInstance(DefaultSqlSession.class.getClassLoader(), new Class[]{mapperClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                String nameSpace = method.getDeclaringClass().getName();

                String methodName = method.getName();
                String statementId = nameSpace + "." + methodName;

                return execute(method, statementId, args);
            }
        });
        return (T) proxyInstance;
    }

    private Object execute(Method method, String statementId, Object[] args) throws Exception {
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        Map<String, MappedStatement> mappedStatementMap = configuration.getMappedStatementMap();
        MappedStatement mappedStatement = mappedStatementMap.get(statementId);
        String sql = mappedStatement.getSql();
        String[] s = sql.split(" ");
        String command = s[0];
        Object result = null;
        if (command.equalsIgnoreCase("insert")) {
            simpleExecutor.insert(configuration, statementId, args);
        } else if (command.equalsIgnoreCase("delete")) {
            simpleExecutor.delete(configuration, statementId, args);
        } else if (command.equalsIgnoreCase("update")) {
            simpleExecutor.update(configuration, statementId, args);
        } else {
            Type genericReturnType = method.getGenericReturnType();
            if (genericReturnType instanceof ParameterizedType) {
                List<Object> objects = selectList(statementId, args);
                result = objects;
            } else {
                result = selectOne(statementId, args);
            }
        }
        return result;
    }


    @Override
    public <T> List<T> selectList(String statementId, Object... params) throws SQLException, IntrospectionException, NoSuchFieldException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException {
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        List<Object> list = simpleExecutor.query(configuration, statementId, params);
        return (List<T>) list;
    }

    @Override
    public <T> T selectOne(String statementId, Object... args) throws SQLException, IntrospectionException, NoSuchFieldException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException {
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        List<Object> list = simpleExecutor.query(configuration, statementId, args);
        return list.size() == 0 ? null : (T) list.get(0);
    }

    @Override
    public void insertOne(String statementId, Object... params) {

    }

    @Override
    public void deleteByPrimaryKey(String statementId, Object... params) {

    }

    @Override
    public void updateByPrimaryKey(String statementId, Object... params) {

    }

    @Override
    public <E> List<E> selectAll(String statementId, Object... params) throws SQLException, IntrospectionException, NoSuchFieldException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException {
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        List<Object> list = simpleExecutor.query(configuration, statementId, params);
        return (List<E>) list;
    }

    @Override
    public <E> E selectByPrimaryKey(String statementId, Object... params) throws SQLException, IntrospectionException, NoSuchFieldException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException {
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        List<Object> list = simpleExecutor.query(configuration, statementId, params);
        return (E) list.get(0);
    }

    @Override
    public <E> List<E> selectByCondition(String statementId, Object... params) throws SQLException, IntrospectionException, NoSuchFieldException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException {
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        List<Object> list = simpleExecutor.query(configuration, statementId, params);
        return (List<E>) list;
    }


    public Configuration getConfiguration() {
        return configuration;
    }
}
