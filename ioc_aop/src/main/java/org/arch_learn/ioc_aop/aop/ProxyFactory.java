package org.arch_learn.ioc_aop.aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.arch_learn.ioc_aop.annos.Transactional;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyFactory {
    public static Object getJDKProxyObject(Object target) {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Object result = null;
                try {
                    if (method.isAnnotationPresent(Transactional.class)) {
                        TransactionManager.beginTransaction();
                    }
                    result = method.invoke(target, args);
                    TransactionManager.commit();
                } catch (Exception e) {
                    TransactionManager.rollBack();
                    throw e;
                } finally {

                }
                return result;
            }
        });
    }

    public static Object getCglibProxyInstance(Object object) {
        return Enhancer.create(object.getClass(), new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                Object result = null;
                try {
                    result = method.invoke(o, objects);
                } catch (Exception e) {
                    throw e;
                } finally {

                }
                return result;
            }
        });
    }
}
