package org.zmqy.erp.model.mis.entity.log;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tLoginLog")
public class LoginLog {
    private String id;

    private String userId;

    private String ip;

    private String loginClientNo;

    private Boolean areSuccess;

    private Date loginTime;

    private Date logoutTime;

    private String createUserId;

    private Date createTime;

    private Date lastUpdateTime;
}