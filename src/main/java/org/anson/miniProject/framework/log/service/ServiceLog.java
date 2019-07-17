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
    String description();

    /**
     * add(新增)
     */
    // String operTypeKey();

    String valKey() default "no";

    String pkKey() default "id";

    PkClassFrom pkCalssFrom() default PkClassFrom.INPUT;   // param or return

    /**
     * 主表
     */
    String mainTableName();
}