package org.springframework.plugin.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Service;

import java.lang.annotation.*;

/**
 * @author chenzhh
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Service
public @interface Plugin {
    @AliasFor(
            annotation = Service.class
    )
    String value() default "";

    /**
     *
     * @return
     */
    int  order() default 1;
}
