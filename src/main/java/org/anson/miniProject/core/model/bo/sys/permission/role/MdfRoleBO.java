package org.anson.miniProject.core.model.bo.sys.permission.role;

import lombok.Data;
import org.anson.miniProject.core.model.dmo.sys.permission.role.MdfRoleDMO;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

import java.util.List;

@Data
public class MdfRoleBO {
    private String id;

    private String parentRoleId;

    private String no;
    private String name;
    private String description;

    private List<String> leftUserIdList;

    private List<String> leftResIdList;

    private static final BeanCopier toAddRoleDMOCopier = BeanCopier.create(MdfRoleBO.class, MdfRoleDMO.class, false);

    public static MdfRoleDMO toAddRoleDMO(MdfRoleBO dmo) throws InstantiationException, IllegalAccessException {
        return BeanHelper.beanToBean(dmo, MdfRoleDMO.class, toAddRoleDMOCopier);
    }
}
