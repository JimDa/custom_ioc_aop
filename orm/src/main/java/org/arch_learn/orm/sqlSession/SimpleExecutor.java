package org.arch_learn.orm.sqlSession;

import org.arch_learn.orm.config.BoundSql;
import org.arch_learn.orm.po.Configuration;
import org.arch_learn.orm.po.MappedStatement;
import org.arch_learn.orm.utils.ConnectionUtils;
import org.arch_learn.orm.utils.GenericTokenParser;
import org.arch_learn.orm.utils.ParameterMapping;
import org.arch_learn.orm.utils.ParameterMappingTokenHandler;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SimpleExecutor implements Executor {
    @Override
    public <E> List<E> query(Configuration configuration, String statementId, Object... params) throws SQLException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException, IntrospectionException, InstantiationException, InvocationTargetException {
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        //1.注册驱动，获取连接
        Connection connection = ConnectionUtils.getCurrentConnection();
        //2.获取sql语句，转换sql语句
        String sql = mappedStatement.getSql();
        BoundSql boundSql = getBoundSql(sql);
        //3.获取预处理对象
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlText());
        //4.设置参数
        String parameterType = mappedStatement.getParameterType();
        Class<?> clazz = getClassType(parameterType);

        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        for (int i = 0; i < parameterMappings.size(); i++) {
            ParameterMapping parameterMapping = parameterMappings.get(i);
            String content = parameterMapping.getContent();
            Field declaredField = clazz.getDeclaredField(content);
            //设置暴力访问
            declaredField.setAccessible(true);
            Object o = declaredField.get(params[0]);
            System.out.println(o.toString());
            //preparedStatement里面的参数的索引是从1开始的，所以这里要＋1
            preparedStatement.setObject(i + 1, o);
        }
        //5.执行sql
        ResultSet resultSet = preparedStatement.executeQuery();

        String resultType = mappedStatement.getResultType();
        Class<?> resultTypeClazz = getClassType(resultType);

        ArrayList<Object> resultList = new ArrayList<>();
        //6.封装返回结果集
        while (resultSet.next()) {
            ResultSetMetaData metaData = resultSet.getMetaData();
            Object o = resultTypeClazz.newInstance();
            for (int i = 1; i < metaData.getColumnCount() + 1; i++) {
                //字段名
                String columnName = metaData.getColumnName(i);
                //字段值
                Object value = resultSet.getObject(columnName);

                //使用反射或者内省，根据数据库表和实体的对应关系，完成封装
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, resultTypeClazz);
                Method writeMethod = propertyDescriptor.getWriteMethod();
                writeMethod.invoke(o, value);
            }
            resultList.add(o);
        }
        return (List<E>) resultList;
    }

    @Override
    public void insert(Configuration configuration, String statementId, Object... params) throws Exception {
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        //1.注册驱动，获取连接

        Connection connection = ConnectionUtils.getCurrentConnection();
        //2.获取sql语句，转换sql语句
        String sql = mappedStatement.getSql();
        BoundSql boundSql = getBoundSql(sql);
        //3.获取预处理对象
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlText());
        //4.设置参数
        String parameterType = mappedStatement.getParameterType();
        Class<?> clazz = getClassType(parameterType);

        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        for (int i = 0; i < parameterMappings.size(); i++) {
            ParameterMapping parameterMapping = parameterMappings.get(i);
            String content = parameterMapping.getContent();
            Field declaredField = clazz.getDeclaredField(content);
            //设置暴力访问
            declaredField.setAccessible(true);
            Object o = declaredField.get(params[0]);
//            System.out.println(o.toString());
            //preparedStatement里面的参数的索引是从1开始的，所以这里要＋1
            preparedStatement.setObject(i + 1, o);
        }

        boolean b = preparedStatement.execute();
    }

    @Override
    public void delete(Configuration configuration, String statementId, Object[] args) throws SQLException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        //1.注册驱动，获取连接
        Connection connection = ConnectionUtils.getCurrentConnection();
        //2.获取sql语句，转换sql语句
        String sql = mappedStatement.getSql();
        BoundSql boundSql = getBoundSql(sql);
        //3.获取预处理对象
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlText());
        //4.设置参数
        String parameterType = mappedStatement.getParameterType();
        Class<?> clazz = getClassType(parameterType);

        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        for (int i = 0; i < parameterMappings.size(); i++) {
            ParameterMapping parameterMapping = parameterMappings.get(i);
            String content = parameterMapping.getContent();
            Field declaredField = clazz.getDeclaredField(content);
            //设置暴力访问
            declaredField.setAccessible(true);
            Object o = declaredField.get(args[0]);
//            System.out.println(o.toString());
            //preparedStatement里面的参数的索引是从1开始的，所以这里要＋1
            preparedStatement.setObject(i + 1, o);
        }

        boolean b = preparedStatement.execute();
    }

    @Override
    public void update(Configuration configuration, String statementId, Object[] args) throws SQLException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        //1.注册驱动，获取连接
        Connection connection = ConnectionUtils.getCurrentConnection();
        //2.获取sql语句，转换sql语句
        String sql = mappedStatement.getSql();
        BoundSql boundSql = getBoundSql(sql);
        //3.获取预处理对象
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlText());
        //4.设置参数
        String parameterType = mappedStatement.getParameterType();
        Class<?> clazz = getClassType(parameterType);

        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        for (int i = 0; i < parameterMappings.size(); i++) {
            ParameterMapping parameterMapping = parameterMappings.get(i);
            String content = parameterMapping.getContent();
            Field declaredField = clazz.getDeclaredField(content);
            //设置暴力访问
            declaredField.setAccessible(true);
            Object o = declaredField.get(args[0]);
//            System.out.println(o.toString());
            //preparedStatement里面的参数的索引是从1开始的，所以这里要＋1
            preparedStatement.setObject(i + 1, o);
        }
        preparedStatement.executeUpdate();
    }

    private Class<?> getClassType(String parameterType) throws ClassNotFoundException {
        Class<?> clazz = null;
        if (parameterType != null) {
            clazz = Class.forName(parameterType);
        }
        return clazz;
    }

    /**
     * 完成对#{}的处理：1.将#{}使用？来代替；2.解析出#{}里面的值进行存储
     *
     * @param sql
     * @return
     */
    private BoundSql getBoundSql(String sql) {
        //标记处理类：配置标记解析器来完成对占位符的解析处理
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", parameterMappingTokenHandler);
        //#{参数名}换成了?
        String parsedSql = genericTokenParser.parse(sql);
        //解析出来的参数名称#{}里面的
        List<ParameterMapping> parameterMappings = parameterMappingTokenHandler.getParameterMappings();

        return new BoundSql(parsedSql, parameterMappings);
    }
}
