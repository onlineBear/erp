package org.anson.miniProject.core.model.bo.sys.permission.role;

import lombok.Data;
import org.anson.miniProject.core.model.dmo.sys.permission.role.AddRoleDMO;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

@Data
public class AddRoleBO {
    private String parentRoleId;

    private String no;
    private String name;
    private String description;

    private static final BeanCopier toAddRoleDMOCopier = BeanCopier.create(AddRoleBO.class, AddRoleDMO.class, false);

    public static AddRoleDMO toAddRoleDMO(AddRoleBO dmo) throws InstantiationException, IllegalAccessException {
        return BeanHelper.beanToBean(dmo, AddRoleDMO.class, toAddRoleDMOCopier);
    }
}
