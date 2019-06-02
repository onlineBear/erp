package org.zmqy.erp.model.mis.entity.sys.security;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tResourceLang")
public class ResourceLang {
    private String id;

    private String resId;
    public static final String RESID = "resId";

    private String langId;
    public static final String LANGID = "langId";

    private String resName;

    private String resDesc;

    private String createUserId;

    private Date createTime;

    private Date lastUpdateTime;
}