package org.anson.miniProject.domain.internal.roleResource.impl;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.anson.miniProject.domain.base.BasePO;

@Data
@TableName("roleResource")
class RoleResource extends BasePO {
    private String roleId;
    private String resourceId;

    public static final String ROLEID = "roleId";
    public static final String RESOURCEID = "resourceId";

    private static final String __TABLENAME = "roleResource";
    @Override
    public String TABLENAME() {
        return __TABLENAME;
    }
}
