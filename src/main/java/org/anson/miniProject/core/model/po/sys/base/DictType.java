package org.anson.miniProject.core.model.po.sys.base;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.anson.miniProject.core.model.po.BasePO;

@Data
@TableName("dictType")
public class DictType extends BasePO {
    private String no;
    private String name;
    private String description;

    public static final String __TABLENAME = "dictType";

    public static final String NO = "no";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";

    @Override
    public String TABLENAME() {
        return __TABLENAME;
    }
}
