package org.zmqy.erp.model.mis.bo.sys.security;

import lombok.Data;
import org.springframework.cglib.beans.BeanCopier;
import org.zmqy.erp.model.mis.entity.sys.security.RoleLang;

import java.util.Date;

@Data
public class RoleLangBo {
    private String id;

    private String roleId;

    private String langId;

    private String roleName;

    private String roleDesc;

    private String createUserId;

    private Date createTime;

    private Date lastUpdateTime;

    private static final BeanCopier bo2entityCopier = BeanCopier.create(RoleLangBo.class, RoleLang.class, false);

    public static RoleLang bo2entity(RoleLangBo bo){
        if (bo == null){
            return null;
        }

        RoleLang entity = new RoleLang();

        bo2entityCopier.copy(bo, entity, null);

        return entity;
    }
}
