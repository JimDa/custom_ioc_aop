package org.arch_learn.ioc_aop.annos;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Autowired {
    String value() default "";
}
