package org.zmqy.erp.model.mis.entity.sys.base;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tCommonTypeLang")
public class CommonTypeLang {
    private String id;
    public static final String ID = "id";

    private String comTypeId;
    public static final String COMTYPEID = "comTypeId";

    private String langId;
    public static final String LANGID = "langId";

    private String comTypeName;
    public static final String COMTYPENAME = "comTypeName";

    private String comTypeDesc;
    public static final String COMTYPEDESC = "comTypeDesc";

    private String createUserId;
    public static final String CREATEUSERID = "createUserId";

    private Date createTime;
    public static final String CREATETIME = "createTime";

    private Date lastUpdateTime;
    public static final String LASTUPDATETIME = "lastUpdateTime";
}
