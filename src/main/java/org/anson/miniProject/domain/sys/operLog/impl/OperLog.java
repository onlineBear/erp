package org.anson.miniProject.domain.sys.operLog.impl;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.anson.miniProject.domain.base.BasePO;

import java.util.Date;

@Data
@TableName("oper_log")
class OperLog extends BasePO {
    private String operUserId;
    private String operMenuId;

    private String clientKey;

    private Date operTime;
    private String description;
    private Boolean areSuccessful;
    private String failReason;
    private String pk;
    private String ipv4;
    private Double longitude;
    private Double latitude;

    public static final String OPERUSERID = "operUserId";
    public static final String OPERMENUID = "operMenuId";
    public static final String OPERTYPEKEY = "operTypeKey";
    public static final String CLIENTKEY = "clientKey";
    public static final String OPERTIME = "operTime";
    public static final String IPV4 = "ipv4";
    public static final String PK = "pk";
    public static final String DESCRIPTION = "description";
    public static final String ARESUCCESSFUL = "areSuccessful";
    public static final String FAILREASON = "failReason";
    public static final String LONGITUDE = "longitude";
    public static final String LATITUDE = "latitude";

    private static final String __TABLENAME = "operLog";
    @Override
    public String TABLENAME() {
        return __TABLENAME;
    }
}
