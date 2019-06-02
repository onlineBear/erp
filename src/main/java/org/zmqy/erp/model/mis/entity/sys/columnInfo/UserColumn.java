package org.zmqy.erp.model.mis.entity.sys.columnInfo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tUserColumn")
public class UserColumn {
    private String id;

    private String columnId;
    public static final String COLUMNID = "columnId";

    private String userId;
    public static final String USERID = "userId";

    private String columnName;

    private Integer seq;

    private Boolean areSysHidden;

    private Boolean areUserHidden;

    private Integer width;

    private String createUserId;

    private Date createTime;

    private Date lastUpdateTime;
}