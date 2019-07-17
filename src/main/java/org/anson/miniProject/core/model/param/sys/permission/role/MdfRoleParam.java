package org.anson.miniProject.core.model.param.sys.permission.role;

import lombok.Data;
import org.anson.miniProject.core.model.bo.sys.permission.role.RoleBO;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

import java.util.List;

@Data
public class MdfRoleParam {
    private String id;

    private String parentRoleId;

    private String no;
    private String name;
    private String description;

    private List<String> leftUserIdList;

    private List<String> leftResIdList;

    private static final BeanCopier toBOCopier = BeanCopier.create(MdfRoleParam.class, RoleBO.class, false);

    public static RoleBO toBO(MdfRoleParam param) throws InstantiationException, IllegalAccessException {
        return BeanHelper.beanToBean(param, RoleBO.class, toBOCopier);
    }
}
