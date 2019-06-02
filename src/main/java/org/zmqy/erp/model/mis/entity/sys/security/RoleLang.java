package org.zmqy.erp.model.mis.entity.sys.security;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tRoleLang")
public class RoleLang {
    private String id;
    public static final String ID = "id";

    private String roleId;
    public static final String ROLEID = "roleId";

    private String langId;
    public static final String LANGID = "langId";

    private String roleName;
    public static final String ROLENAME = "roleName";

    private String roleDesc;
    public static final String ROLEDESC = "roleDesc";

    private String createUserId;
    public static final String CREATEUSERID = "createUserId";

    private Date createTime;
    public static final String CREATETIME = "createTime";

    private Date lastUpdateTime;
    public static final String LASTUPDATETIME = "lastUpdateTime";
}
