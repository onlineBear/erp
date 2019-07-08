package org.anson.miniProject.framework.log.service;

import java.lang.annotation.*;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ServiceLog {
    /**
     * 订单
     */
    String value();

    /**
     * add(新增)
     */
    String operTypeKey();

    /**
     * 主表
     */
    String mainTableName();
}
