package org.springframework.extension.annotation;

import java.lang.annotation.*;

/**
 * 扩展spring 服务
 * @author chenzhh
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Extension {
    /**
     * 实体名称
     * @return
     */
    String [] overrideBeanNames () default {};


    /**
     * 实例化bean
     * @return
     */
    String   beanName() default  "";

}
