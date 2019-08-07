package org.anson.miniProject.framework.log.service;

import org.anson.miniProject.constrant.dict.ClientEnum;

import java.lang.annotation.*;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface UserOperLog {
    /**
     * 订单
     */
    String description();

    String valKey();

    ClientEnum clientKey();

    String menuId();
}