package org.anson.miniProject.model.entity.sys;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("deletedRecord")
public class DelRecord {
    private String id;

    private String tableName;
    private String pk;
    private String record;
    private String deletedUserId;
    private Date deletedTime;

    private String createUserId;
    private Date createTime;
    private Date lastUpdateTime;

    public static final String ID = "id";
    public static final String TABLENAME = "tableName";
    public static final String RECORD = "record";
    public static final String DELETEDUSERID = "deletedUserId";
    public static final String DELETEDTIME = "deletedTime";
    public static final String CREATEUSERID = "createUserId";
    public static final String CREATETIME = "createTime";
    public static final String LASTUPDATETIME = "lastUpdateTime";
    public static final String __TABLENAME = "deletedRecord";
}
