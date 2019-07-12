package org.anson.miniProject.core.model.po.sys.log;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.anson.miniProject.core.model.po.BasePo;

import java.util.Date;

@Data
@TableName("operLog")
public class OperLog extends BasePo {
    private String operUserId;
    private String operMenuId;

    // private String operTypeKey;
    private String clientKey;

    private Date operTime;
    private String description;
    private Boolean areSuccessful;
    private String failReason;
    private String pk;
    private String mainTableName;
    private String ipv4;
    private Double longitude;
    private Double latitude;

    public static final String __TABLENAME = "operLog";

    public static final String OPERUSERID = "operUserId";
    public static final String OPERMENUID = "operMenuId";
    public static final String OPERTYPEKEY = "operTypeKey";
    public static final String CLIENTKEY = "clientKey";
    public static final String OPERTIME = "operTime";
    public static final String IPV4 = "ipv4";
    public static final String PK = "pk";
    public static final String MAINTABLENAME = "mainTableName";
    public static final String DESCRIPTION = "description";
    public static final String ARESUCCESSFUL = "areSuccessful";
    public static final String FAILREASON = "failReason";
    public static final String LONGITUDE = "longitude";
    public static final String LATITUDE = "latitude";

    @Override
    public String TABLENAME() {
        return __TABLENAME;
    }
}
