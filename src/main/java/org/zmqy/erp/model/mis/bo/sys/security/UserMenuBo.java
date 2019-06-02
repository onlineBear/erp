package org.zmqy.erp.model.mis.bo.sys.security;

import lombok.Data;
import org.springframework.cglib.beans.BeanCopier;
import org.zmqy.erp.model.mis.entity.sys.security.UserMenu;

import java.util.Date;

@Data
public class UserMenuBo {
    private String id;

    private String userId;

    private String menuId;

    private String createUserId;

    private Date createTime;

    private Date lastUpdateTime;

    private static final BeanCopier bo2entityCopier = BeanCopier.create(UserMenuBo.class, UserMenu.class, false);

    public static UserMenu bo2entity(UserMenuBo bo){
        if (bo == null){
            return null;
        }

        UserMenu entity = new UserMenu();

        bo2entityCopier.copy(bo, entity, null);

        return entity;
    }
}
