package com.wooshop.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheRemove {

    /**
     * 需要清除的大类 例如 autocms 所有缓存
     *
     * @return
     */
    String value() default "";


    /**
     * 需要清除的具体的额类型
     *
     * @return
     */
    String[] key() default {};
}
