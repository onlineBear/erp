package org.zmqy.erp.model.mis.entity.sys.columnInfo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tDtlColumn")
public class DtlColumn {
    private String id;

    private String menuId;
    public static final String MENUID = "menuId";

    private String columnNo;
    public static final String COLUMNNO = "columnNo";

    private String arrayNo;
    public static final String ARRAYNO = "arrayNo";

    private String dataTypeNo;

    private String pageTypeNo;
    public static final String PAGETYPENO = "pageTypeNo";

    private Integer seq;

    private Boolean areHidden;

    private Boolean areSysRequired;

    private Boolean areUserRequired;

    private Integer width;

    private String alias;

    private String formula;

    private String createUserId;

    private Date createTime;

    private Date lastUpdateTime;
}