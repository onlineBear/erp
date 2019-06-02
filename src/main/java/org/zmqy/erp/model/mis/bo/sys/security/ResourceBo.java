package org.zmqy.erp.model.mis.bo.sys.security;

import lombok.Data;
import org.springframework.cglib.beans.BeanCopier;
import org.zmqy.erp.model.mis.entity.sys.security.Resource;

import java.util.Date;
import java.util.List;

@Data
public class ResourceBo {
    private String id;

    private String menuId;

    private String resUrl;

    private String resOperNo;

    private String createUserId;

    private Date createTime;

    private Date lastUpdateTime;

    private List<ResourceLangBo> resourceLangBoList;

    private static final BeanCopier bo2entityCopier = BeanCopier.create(ResourceBo.class, Resource.class, false);

    public static Resource bo2entity(ResourceBo bo){
        if (bo == null){
            return null;
        }

        Resource entity = new Resource();

        bo2entityCopier.copy(bo, entity, null);

        return entity;
    }
}
