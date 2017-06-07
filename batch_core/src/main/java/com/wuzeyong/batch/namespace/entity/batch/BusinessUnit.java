package com.wuzeyong.batch.namespace.entity.batch;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ��ѯ����Ԫ
 * Created by WUZEYONG089 on 2017/4/27.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface BusinessUnit {
    String value() default "";
}
