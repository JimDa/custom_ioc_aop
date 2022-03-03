package org.arch_learn.ioc_aop.ioc;

import org.arch_learn.ioc_aop.annos.*;
import org.arch_learn.ioc_aop.aop.ProxyFactory;
import org.arch_learn.ioc_aop.utils.ScanUtils;
import org.arch_learn.orm.io.Resources;
import org.arch_learn.orm.sqlSession.SqlSession;
import org.arch_learn.orm.sqlSession.SqlSessionFactory;
import org.arch_learn.orm.sqlSession.SqlSessionFactoryBuilder;
import org.arch_learn.orm.utils.SessionUtils;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;

public class BeanFactory {
    private static HashMap<String, Object> singletonPool = new HashMap<String, Object>();

    public static void start() {
        InputStream inputStream = BeanFactory.class.getClassLoader().getResourceAsStream("applications.properties");
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
            //扫描java类
            ScanUtils.doScanPackage(properties.getProperty("scanPath"));
            //初始化ioc单例池
            initSingletonPool();
            //属性注入
            doAutowired();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    static {
//        InputStream inputStream = BeanFactory.class.getClassLoader().getResourceAsStream("applications.properties");
//        Properties properties = new Properties();
//        try {
//            properties.load(inputStream);
//            //扫描java类
//            ScanUtils.doScanPackage(properties.getProperty("scanPath"));
//            //初始化ioc单例池
//            initSingletonPool();
//            //属性注入
//            doAutowired();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    private static void doAutowired() {
        if (singletonPool.size() == 0) return;
        singletonPool.entrySet().stream()
                .forEach(v -> {
                    Field[] declaredFields = v.getValue().getClass().getDeclaredFields();
                    for (Field f : declaredFields) {
                        if (!f.isAnnotationPresent(Autowired.class)) {
                            continue;
                        }
                        Autowired annotation = f.getAnnotation(Autowired.class);
                        String value = annotation.value();
                        Object o = null;
                        if (value.length() == 0) {
                            String name = f.getType().getClass().getName();
                            o = singletonPool.get(name);
                        } else {
                            o = singletonPool.get(value);
                        }
                        f.setAccessible(true);
                        try {
                            f.set(v.getValue(), o);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private static void initSingletonPool() throws PropertyVetoException, DocumentException {
        Set<String> classNames = ScanUtils.getClassNames();
        if (classNames.size() == 0) return;

        try {
            for (String name : classNames) {
                Class<?> clazz = Class.forName(name);
                if (clazz.isAnnotationPresent(Component.class)) {
                    initAndPutIntoSingletonPool(clazz, "");
                } else if (clazz.isAnnotationPresent(Controller.class)) {
                    String value = clazz.getAnnotation(Controller.class).value();
                    String alias = value.length() == 0 ? clazz.getName() : value;
                    initAndPutIntoSingletonPool(clazz, alias);
                } else if (clazz.isAnnotationPresent(Service.class)) {
                    String value = clazz.getAnnotation(Service.class).value();
                    String alias = value.length() == 0 ? clazz.getName() : value;

                    //这里默认所有的service层被@Service注解的都使用代理对象
//                    Object jdkProxyObject = ProxyFactory.getJDKProxyObject(clazz.newInstance());
//                    singletonPool.put(alias, jdkProxyObject);
                    initAndPutIntoSingletonPool(clazz, alias);
                } else if (clazz.isAnnotationPresent(Configuration.class)) {
                    Object o = clazz.newInstance();
                    Method[] methods = clazz.getMethods();
                    for (Method m : methods) {
                        if (m.isAnnotationPresent(Bean.class)) {
                            Bean beanAnno = m.getAnnotation(Bean.class);
                            String value = beanAnno.value();
                            Object result = m.invoke(o);
                            singletonPool.put(value.length() == 0 ? m.getName() : value, result);
                        }
                    }
                } else if (clazz.isAnnotationPresent(Mapper.class)) {
                    Mapper annotation = clazz.getAnnotation(Mapper.class);
                    String value = annotation.value();
                    SqlSession sqlSession = SessionUtils.getDefaultSqlSession();
                    Object sqlSessionMapper = sqlSession.getMapper(clazz);
                    singletonPool.put(value.length() == 0 ? clazz.getName() : value, sqlSessionMapper);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void initAndPutIntoSingletonPool(Class<?> clazz, String name) throws InstantiationException, IllegalAccessException {
        Object o = clazz.newInstance();
        if (name.length() != 0) {
            singletonPool.put(name, o);
        } else {
            singletonPool.put(clazz.getName(), o);
        }
    }

    public static Object getBean(String key) {
        return singletonPool.get(key);
    }
}
