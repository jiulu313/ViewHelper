package com.zh.viewhelperlib;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by zhanghongjun on 16/9/11.
 */


@Target(ElementType.FIELD) //作用在字段上
@Retention(RetentionPolicy.RUNTIME) //生成周期为运行时
public @interface Bind {
    int value();

}
