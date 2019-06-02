package org.zmqy.erp.model.mis.bo.sys.base;

import lombok.Data;
import org.springframework.cglib.beans.BeanCopier;
import org.zmqy.erp.model.mis.entity.sys.base.CommonTypeLang;

import java.util.Date;

@Data
public class CommonTypeLangBo {
    private String id;

    private String comTypeId;

    private String langId;

    private String comTypeName;

    private String comTypeDesc;

    private String createUserId;

    private Date createTime;

    private Date lastUpdateTime;

    private static final BeanCopier bo2entityCopier = BeanCopier.create(CommonTypeLangBo.class, CommonTypeLang.class, false);

    public static CommonTypeLang bo2entity(CommonTypeLangBo bo){
        if (bo == null){
            return null;
        }

        CommonTypeLang entity = new CommonTypeLang();

        bo2entityCopier.copy(bo, entity, null);

        return entity;
    }
}
