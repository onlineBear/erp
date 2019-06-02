package org.zmqy.erp.model.mis.bo.sys.security;

import lombok.Data;
import org.springframework.cglib.beans.BeanCopier;
import org.zmqy.erp.model.mis.entity.sys.security.Resource;
import org.zmqy.erp.model.mis.entity.sys.security.RoleMenu;

import java.util.Date;

@Data
public class RoleMenuBo {
    private String id;

    private String roleId;

    private String menuId;

    private String createUserId;

    private Date createTime;

    private Date lastUpdateTime;

    private static final BeanCopier bo2entityCopier = BeanCopier.create(RoleMenuBo.class, RoleMenu.class, false);

    public static RoleMenu bo2entity(RoleMenuBo bo){
        if (bo == null){
            return null;
        }

        RoleMenu entity = new RoleMenu();

        bo2entityCopier.copy(bo, entity, null);

        return entity;
    }
}
