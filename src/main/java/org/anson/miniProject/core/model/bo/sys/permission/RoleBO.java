package org.anson.miniProject.core.model.bo.sys.permission;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.anson.miniProject.core.model.po.sys.permission.Role;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class RoleBO {
    private String id;
    private String createUserId;
    private Date createTime;
    private Date lastUpdateTime;

    private RoleBO parentRoleBO;
    private List<RoleBO> childRoleBO;

    private String no;
    private String name;
    private String description;

    private List<String> resourceIdList;
    private List<String> userIdList;

    private static final BeanCopier toRoleCopier = BeanCopier.create(RoleBO.class, Role.class, false);

    public Role toRole() throws InstantiationException, IllegalAccessException {
        return BeanHelper.beanToBean(this, Role.class, toRoleCopier);
    }

    @Tolerate
    public RoleBO(){}
}
