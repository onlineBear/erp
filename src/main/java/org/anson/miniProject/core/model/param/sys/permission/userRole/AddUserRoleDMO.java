package org.anson.miniProject.core.model.param.sys.permission.userRole;

import lombok.Data;
import org.anson.miniProject.core.model.po.sys.permission.UserRole;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

@Data
public class AddUserRoleDMO {
    private String userId;
    private String roleId;

    private static final BeanCopier toUserRoleCopier = BeanCopier.create(AddUserRoleDMO.class, UserRole.class, false);

    public static UserRole toUserRole(AddUserRoleDMO dmo) throws InstantiationException, IllegalAccessException {
        return BeanHelper.beanToBean(dmo, UserRole.class, toUserRoleCopier);
    }
}
