package org.zmqy.erp.model.mis.entity.sys.columnInfo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tHeadColumnLang")
public class HeadColumnLang {
    private String id;

    private String headColumnId;
    public static final String HEADCOLUMNID = "headColumnId";

    private String langId;
    public static final String LANGID = "langId";

    private String columnName;

    private String createUserId;

    private Date createTime;

    private Date lastUpdateTime;
}