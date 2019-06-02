package org.zmqy.erp.model.mis.bo.sys.security;

import lombok.Data;
import org.springframework.cglib.beans.BeanCopier;
import org.zmqy.erp.model.mis.entity.sys.security.ResourceLang;

import java.util.Date;

@Data
public class ResourceLangBo {
    private String id;

    private String resId;

    private String langId;

    private String resName;

    private String resDesc;

    private String createUserId;

    private Date createTime;

    private Date lastUpdateTime;

    private static final BeanCopier bo2entityCopier = BeanCopier.create(ResourceLangBo.class, ResourceLang.class, false);

    public static ResourceLang bo2entity(ResourceLangBo bo){
        if (bo == null){
            return null;
        }

        ResourceLang entity = new ResourceLang();

        bo2entityCopier.copy(bo, entity, null);

        return entity;
    }
}
