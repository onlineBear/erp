package org.zmqy.erp.model.mis.entity.sys.security;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tUserMenu")
public class UserMenu {
    private String id;

    private String userId;
    public static final String USERID = "userId";

    private String menuId;
    public static final String MENUID = "menuId";

    private String createUserId;

    private Date createTime;

    private Date lastUpdateTime;
}
