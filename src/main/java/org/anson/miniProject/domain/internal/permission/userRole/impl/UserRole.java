package org.anson.miniProject.domain.internal.permission.userRole.impl;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.anson.miniProject.domain.base.BasePO;

@Data
@TableName("userRole")
class UserRole extends BasePO {
    private String userId;
    private String roleId;

    public static final String USERID = "userId";
    public static final String ROLEID = "roleId";

    private static final String __TABLENAME = "userRole";
    @Override
    public String TABLENAME() {
        return __TABLENAME;
    }
}
