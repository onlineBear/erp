package org.zmqy.erp.model.mis.entity.sys.security;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tResource")
public class Resource {
    private String id;
    public static final String ID = "id";

    private String menuId;

    private String resUrl;
    public static final String RESURL = "resUrl";

    private String resOperNo;

    private String createUserId;

    private Date createTime;

    private Date lastUpdateTime;
}
