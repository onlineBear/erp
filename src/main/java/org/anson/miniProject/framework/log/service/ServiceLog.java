package org.anson.miniProject.framework.log.service;

import org.anson.miniProject.core.model.po.sys.Dict;

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

    Class<?> pkClass() default Dict.class;

    String pkKey() default "id";

    String pkParamType() default "inputParam";

    Class<?> valClass() default Dict.class;

    String valKey() default "no";
}
