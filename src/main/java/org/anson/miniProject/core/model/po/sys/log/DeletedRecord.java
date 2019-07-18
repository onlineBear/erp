package org.anson.miniProject.core.model.po.sys.log;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import org.anson.miniProject.core.model.po.BasePO;

import java.util.Date;

@Data
@Builder
@TableName("deletedRecord")
public class DeletedRecord extends BasePO {
    private String tableName;
    private String pk;
    private String record;
    private String deletedUserId;
    private Date deletedTime;

    public static final String __TABLENAME = "deletedRecord";

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
