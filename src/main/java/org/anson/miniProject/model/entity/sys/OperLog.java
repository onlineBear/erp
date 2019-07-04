package org.anson.miniProject.model.entity.sys;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.anson.miniProject.model.entity.BaseEntity;

import java.util.Date;

@Data
@TableName("operLog")
public class OperLog extends BaseEntity {
    private String operUserId;
    private String operMenuId;
    private String toMenuId;

    private String operTypeKey;
    private String clientKey;

    private Date operTime;
    private String ipv4;
    private String pk;
    private String mainTableName;
    private String description;
    private Boolean areSuccessful;
    private String failReason;
    private Double longitude;
    private Double latitude;

    public static final String __TABLENAME = "operLog";

    public static final String OPERUSERID = "operUserId";
    public static final String OPERMENUID = "operMenuId";
    public static final String TOMENUID = "toMenuId";
    public static final String OPERTYPEKEY = "operTypeKey";
    public static final String OPERTIME = "operTime";
    public static final String IPV4 = "ipv4";
    public static final String PK = "pk";
    public static final String MAINTABLENAME = "mainTableName";
    public static final String DESCRIPTION = "description";
    public static final String ARESUCCESSFUL = "areSuccessful";
    public static final String FAILREASON = "failReason";
    public static final String LONGITUDE = "longitude";
    public static final String LATITUDE = "latitude";
}
