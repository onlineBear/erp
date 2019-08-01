package org.anson.miniProject.domain.internal.deletedRecord;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.anson.miniProject.domain.base.BasePO;

import java.util.Date;

@Data
@TableName("deleted_record")
class DeletedRecord extends BasePO {
    private String tableName;
    private String pk;
    private String record;
    private Date deletedTime;

    public static final String TABLENAME = "table_name";
    public static final String PK = "pk";
    public static final String RECORD = "record";
    public static final String DELETEDTIME = "deleted_time";

    private static final String __TABLENAME = "deleted_record";
    @Override
    public String TABLENAME() {
        return __TABLENAME;
    }
}
