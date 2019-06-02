package org.zmqy.erp.model.mis.entity.sys.columnInfo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tHeadColumn")
public class HeadColumn {
    private String id;

    private String columnNo;
    public static final String COLUMNNO = "columnNo";

    private String menuId;
    public static final String MENUID = "menuId";

    private String pageTypeNo;
    public static final String PAGETYPENO = "pageTypeNo";

    private Integer seq;

    private Boolean areHidden;

    private Boolean areSysRequired;

    private Boolean areUserRequired;

    private String createUserId;

    private Date createTime;

    private Date lastUpdateTime;
}