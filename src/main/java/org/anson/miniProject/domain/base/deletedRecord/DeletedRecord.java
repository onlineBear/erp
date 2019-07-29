package org.anson.miniProject.domain.base.deletedRecord;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.anson.miniProject.domain.base.BasePO;

import java.util.Date;

@Data
@TableName("deletedRecord")
class DeletedRecord extends BasePO {
    private String tableName;
    private String pk;
    private String record;
    private String deletedUserId;
    private Date deletedTime;

    private static final String __TABLENAME = "deletedRecord";

    public static final String TABLENAME = "tableName";
    public static final String PK = "pk";
    public static final String RECORD = "record";
    public static final String DELETEDUSERID = "deletedUserId";
    public static final String DELETEDTIME = "deletedTime";

    @Override
    public String TABLENAME() {
        return __TABLENAME;
    }
}
