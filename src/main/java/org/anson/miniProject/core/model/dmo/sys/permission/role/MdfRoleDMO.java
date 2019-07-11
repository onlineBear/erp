package org.anson.miniProject.core.model.dmo.sys.permission.role;

import lombok.Data;
import org.anson.miniProject.core.model.po.sys.permission.Role;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

@Data
public class MdfRoleDMO {
    private String id;

    private String parentRoleId;

    private String no;
    private String name;
    private String description;

    private static final BeanCopier toRoleCopier = BeanCopier.create(MdfRoleDMO.class, Role.class, false);

    public static Role toRole(MdfRoleDMO dmo) throws InstantiationException, IllegalAccessException {
        return BeanHelper.beanToBean(dmo, Role.class, toRoleCopier);
    }
}
