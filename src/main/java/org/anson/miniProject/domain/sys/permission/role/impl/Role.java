package org.anson.miniProject.domain.sys.permission.role.impl;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.anson.miniProject.domain.base.BasePO;

@Data
@TableName("role")
class Role extends BasePO {
    private String parentRoleId;

    private String no;
    private String name;
    private String description;

    public static final String PARENTROLEID = "parentRoleId";
    public static final String NO = "no";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";

    private static final String __TABLENAME = "role";
    @Override
    public String TABLENAME() {
        return __TABLENAME;
    }
}
