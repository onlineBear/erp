package org.zmqy.erp.model.mis.bo.sys.security;

import lombok.Data;
import org.springframework.cglib.beans.BeanCopier;
import org.zmqy.erp.model.mis.entity.sys.security.RoleUser;

import java.util.Date;

@Data
public class RoleUserBo {
    private String id;

    private String roleId;

    private String userId;

    private String createUserId;

    private Date createTime;

    private Date lastUpdateTime;

    private static final BeanCopier bo2entityCopier = BeanCopier.create(RoleUserBo.class, RoleUser.class, false);

    public static RoleUser bo2entity(RoleUserBo bo){
        if (bo == null){
            return null;
        }

        RoleUser entity = new RoleUser();

        bo2entityCopier.copy(bo, entity, null);

        return entity;
    }
}
