package org.zmqy.erp.model.mis.entity.sys.base;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tLanguage")
public class Language {
    private String id;
    public static final String ID = "id";

    private String langNo;
    public static final String LANGNO = "langNo";

    private String langName;
    public static final String LANGNAME = "langName";

    private String chineseName;
    public static final String CHINESENAME = "chineseName";

    private Boolean areUsing;
    public static final String AREUSING = "areUsing";

    private String createUserId;
    public static final String CREATEUSERID = "createUserId";

    private Date createTime;
    public static final String CREATETIME = "createTime";

    private Date lastUpdateTime;
    public static final String LASTUPDATETIME = "lastUpdateTime";
}
