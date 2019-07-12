package org.anson.miniProject.core.model.po.sys.permission;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.anson.miniProject.core.model.po.BasePo;

@Data
@TableName("roleResource")
public class RoleResource extends BasePo{
    private String roleId;
    private String resourceId;

    /**
     * 表名
     */
    public static final String __TABLENAME = "roleResource";

    public static final String ROLEID = "roleId";
    public static final String RESOURCEID = "resourceId";

    @Override
    public String TABLENAME() {
        return __TABLENAME;
    }
}
