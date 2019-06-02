package org.zmqy.erp.model.mis.entity.sys.security;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tUserResource")
public class UserResource {
    private String id;

    private String userId;
    public static final String USERID = "userId";

    private String resId;
    public static final String RESID = "resId";

    private String createUserId;

    private Date createTime;

    private Date lastUpdateTime;
}
