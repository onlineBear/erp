package org.anson.miniProject.core.model.dmo.sys.permission.roleResource;

import lombok.Data;
import org.anson.miniProject.core.model.po.sys.permission.RoleResource;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

@Data
public class AddRoleResourceDMO {
    private String roleId;
    private String resourceId;

    private static final BeanCopier toRoleResourceCopier = BeanCopier.create(AddRoleResourceDMO.class, RoleResource.class, false);

    public static RoleResource toRoleResource(AddRoleResourceDMO dmo) throws InstantiationException, IllegalAccessException {
        return BeanHelper.beanToBean(dmo, RoleResource.class, toRoleResourceCopier);
    }
}
