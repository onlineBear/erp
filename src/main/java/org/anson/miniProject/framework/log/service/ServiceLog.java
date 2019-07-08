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
    String operTypeKey();

    /**
     * 主表
     */
    String mainTableName();

    Class<?> valClass();

    String valKey() default "no";

    Class<?> pkClass() default PkDefaultClass.class;

    String pkKey() default "id";

    String pkCalssFrom() default "param";   // param or return
}

class PkDefaultClass{

}