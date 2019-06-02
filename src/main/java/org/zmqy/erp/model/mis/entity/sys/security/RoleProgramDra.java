package org.zmqy.erp.model.mis.entity.sys.security;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
@Data
@TableName("tRoleProgramDra")
public class RoleProgramDra {
    private String id;

    private String programDraId;

    private String roleId;
    public static final String ROLEID = "roleId";

    private String createUserId;

    private Date createUserTime;

    private Date lastUpdateTime;

}