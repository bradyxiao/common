package com.tencent.qcloud.sampilenotepad.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by bradyxiao on 2018/7/10.
 */

@Target(value = {ElementType.METHOD, ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface UserPortrait {
    int id() default -1;

    String name() default "None";

    SexType sex() default SexType.MALE;

    String createTime() default "07/10/2018 11:50:00";
}

