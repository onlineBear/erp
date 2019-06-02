package org.zmqy.erp.model.mis.entity.sys.security;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tRole")
public class Role {
    private String id;

    private String roleNo;

    private String createUserId;

    private Date createTime;

    private Date lastUpdateTime;
}
