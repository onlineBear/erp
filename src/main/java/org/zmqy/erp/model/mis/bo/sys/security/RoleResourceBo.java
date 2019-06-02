package org.zmqy.erp.model.mis.bo.sys.security;

import lombok.Data;
import org.springframework.cglib.beans.BeanCopier;
import org.zmqy.erp.model.mis.entity.sys.security.RoleResource;

import java.util.Date;

@Data
public class RoleResourceBo {
    private String id;

    private String roleId;

    private String resId;

    private String createUserId;

    private Date createTime;

    private Date lastUpdateTime;

    private static final BeanCopier bo2entityCopier = BeanCopier.create(RoleResourceBo.class, RoleResource.class, false);

    public static RoleResource bo2entity(RoleResourceBo bo){
        if (bo == null){
            return null;
        }

        RoleResource entity = new RoleResource();

        bo2entityCopier.copy(bo, entity, null);

        return entity;
    }
}
