package org.anson.miniProject.core.model.dmo.sys.permission.role;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

import java.util.List;

@Data
public class AddRoleParam {
    private String parentRoleId;

    private String no;
    private String name;
    private String description;

    private List<String> resourceIdList;
    private List<String> userIdList;

    private static final BeanCopier toBoCopier = BeanCopier.create(AddRoleParam.class, RoleBO.class, false);

    public static RoleBO toBO(AddRoleParam param) throws InstantiationException, IllegalAccessException {
        RoleBO dmo = BeanHelper.beanToBean(param, RoleBO.class, toBoCopier);

        if (StrUtil.isNotBlank(param.getParentRoleId())) {
            RoleBO parentRoleBO = RoleBO.builder()
                                        .id(param.getParentRoleId())
                                        .build();
            dmo.setParentRoleBO(parentRoleBO);
        }

        return dmo;
    }
}
