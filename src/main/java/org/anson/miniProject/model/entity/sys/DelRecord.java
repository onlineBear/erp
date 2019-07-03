package org.anson.miniProject.model.entity.sys;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.anson.miniProject.model.entity.BaseEntity;

import java.util.Date;

@Data
@TableName("deletedRecord")
public class DelRecord extends BaseEntity {
    private String tableName;
    private String pk;
    private String record;
    private String deletedUserId;
    private Date deletedTime;

    public static final String TABLENAME = "tableName";
    public static final String RECORD = "record";
    public static final String DELETEDUSERID = "deletedUserId";
    public static final String DELETEDTIME = "deletedTime";

    public static final String __TABLENAME = "deletedRecord";
}
