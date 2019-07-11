package org.anson.miniProject.core.model.po.sys.permission;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.anson.miniProject.core.model.po.BasePo;

@Data
@TableName("role")
public class Role extends BasePo{
    private String parentRoleId;

    private String no;
    private String name;
    private String description;

    /**
     * 表名
     */
    public static final String __TABLENAME = "role";

    public static final String PARENTROLEID = "parentRoleId";
    public static final String NO = "no";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
}
