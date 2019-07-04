package org.anson.miniProject.model.entity.sys.log;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.anson.miniProject.model.entity.BaseEntity;

import java.util.Date;

@Data
@TableName("loginLog")
public class LoginLog extends BaseEntity {
    // 伪外键
    private String userId;

    // 数据字典
    private String loginTypeKey;

    private Date operTime;
    private String loginUserNo;
    private Boolean areSuccessful;
    private String failReason;
    private String ipv4;
    private Double longitude;
    private Double latitude;

    public static final String __TABLENAME = "loginLog";

    public static final String USERID = "userId";
    public static final String LOGINTYPEKEY = "loginTypeKey";
    public static final String OPERTIME = "operTime";
    public static final String LOGINUSERNO = "loginUserNo";
    public static final String ARESUCCESSFUL = "areSuccessful";
    public static final String FAILREASON = "failReason";
    public static final String IPV4 = "ipv4";
    public static final String LONGITUDE = "longitude";
    public static final String LATITUDE = "latitude";
}
