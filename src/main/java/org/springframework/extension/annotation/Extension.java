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
    String [] name () default {};
    /**
     * 排序，值越大优先执行
     * @return
     */
    int order() default 1;

}
