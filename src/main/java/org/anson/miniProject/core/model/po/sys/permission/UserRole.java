package org.anson.miniProject.core.model.po.sys.permission;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.anson.miniProject.core.model.po.BasePo;

@Data
@TableName("userRole")
public class UserRole extends BasePo {
    private String userId;
    private String roleId;

    /**
     * 表名
     */
    public static final String __TABLENAME = "userRole";

    public static final String USERID = "userId";
    public static final String ROLEID = "roleId";

    @Override
    public String TABLENAME() {
        return __TABLENAME;
    }
}
