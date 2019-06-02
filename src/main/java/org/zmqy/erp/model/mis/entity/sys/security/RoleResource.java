package org.zmqy.erp.model.mis.entity.sys.security;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tRoleResource")
public class RoleResource {
    private String id;
    public static final String ID = "id";

    private String roleId;
    public static final String ROLEID = "roleId";

    private String resId;
    public static final String RESID = "resId";

    private String createUserId;
    public static final String CREATEUSERID = "createUserId";

    private Date createTime;
    public static final String CREATETIME = "createTime";

    private Date lastUpdateTime;
    public static final String LASTUPDATETIME = "lastUpdateTime";
}
