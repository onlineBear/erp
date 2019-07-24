package org.anson.miniProject.domain.sys.dictType.impl;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.anson.miniProject.domain.base.BasePO;

@Data
@TableName("dictType")
class DictType extends BasePO {
    private String no;
    private String name;
    private String description;

    private static final String _TABLENAME = "dictType";

    public static final String NO = "no";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";

    @Override
    public String TABLENAME() {
        return _TABLENAME;
    }
}
