package org.anson.miniProject.core.model.bo.sys.permission.role;

import lombok.Data;
import org.anson.miniProject.core.model.dmo.sys.permission.role.AddRoleParam;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

import java.util.List;

@Data
public class AddRoleBO {
    private String parentRoleId;

    private String no;
    private String name;
    private String description;

    private List<String> resourceIdList;
    private List<String> userIdList;

    private static final BeanCopier toAddRoleDMOCopier = BeanCopier.create(AddRoleBO.class, AddRoleParam.class, false);

    public static AddRoleParam toAddRoleDMO(AddRoleBO dmo) throws InstantiationException, IllegalAccessException {
        return BeanHelper.beanToBean(dmo, AddRoleParam.class, toAddRoleDMOCopier);
    }
}
