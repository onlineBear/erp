package org.zmqy.erp.model.mis.bo.sys.security;

import lombok.Data;
import org.springframework.cglib.beans.BeanCopier;
import org.zmqy.erp.model.mis.entity.sys.security.Role;

import java.util.Date;
import java.util.List;

@Data
public class RoleBo {
    private String id;

    private String roleNo;

    private String createUserId;

    private Date createTime;

    private Date lastUpdateTime;

    private List<RoleLangBo> roleLangBoList;
    private List<RoleUserBo> roleUserBoList;
    private List<RoleResourceBo> roleResourceBoList;
    private List<RoleMenuBo> roleMenuBoList;

    private static final BeanCopier bo2entityCopier = BeanCopier.create(RoleBo.class, Role.class, false);

    public static Role bo2entity(RoleBo bo){
        if (bo == null){
            return null;
        }

        Role entity = new Role();

        bo2entityCopier.copy(bo, entity, null);

        return entity;
    }
}
