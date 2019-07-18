package org.anson.miniProject.core.model.po.sys.log;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.anson.miniProject.core.model.po.BasePO;

import java.util.Date;

@Data
@TableName("loginLog")
public class LoginLog extends BasePO {
    private String userId;

    private String loginTypeKey;
    private String clientKey;

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
    public static final String CLIENTKEY = "clientKey";
    public static final String OPERTIME = "operTime";
    public static final String LOGINUSERNO = "loginUserNo";
    public static final String ARESUCCESSFUL = "areSuccessful";
    public static final String FAILREASON = "failReason";
    public static final String IPV4 = "ipv4";
    public static final String LONGITUDE = "longitude";
    public static final String LATITUDE = "latitude";

    @Override
    public String TABLENAME() {
        return __TABLENAME;
    }
}
