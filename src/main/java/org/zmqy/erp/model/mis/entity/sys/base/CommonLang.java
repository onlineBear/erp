package org.zmqy.erp.model.mis.entity.sys.base;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tCommonLang")
public class CommonLang {
    private String id;

    private String comId;
    public static final String COMID = "comId";

    private String langId;
    public static final String LANGID = "langId";

    private String comName;

    private String comDesc;

    private String createUserId;

    private Date createTime;

    private Date lastUpdateTime;
}
