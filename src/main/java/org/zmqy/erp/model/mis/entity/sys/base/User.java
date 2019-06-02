package org.zmqy.erp.model.mis.entity.sys.base;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tUser")
public class User {
    private String id;
    public static final String ID = "id";

    private String userNo;
    public static final String USERNO = "userNo";

    private String userName;
    public static final String USERNAME = "userName";

    private String password;
    public static final String PASSWORD = "password";

    private String createUserId;
    public static final String CREATEUSERId = "createUserId";

    private Date createTime;
    public static final String CREATETIME = "createTime";

    private Date lastUpdateTime;
    public static final String LASTUPDATETIME = "lastUpdateTime";
}
