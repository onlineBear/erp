package org.zmqy.erp.model.mis.entity.sys.columnInfo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tDtlColumnLang")
public class DtlColumnLang {
    private String id;

    private String dtlColumnId;
    public static final String DTLCOLUMNID = "dtlColumnId";

    private String langId;
    public static final String LANGID = "langId";

    private String columnName;

    private String createUserId;

    private Date createTime;

    private Date lastUpdateTime;
}