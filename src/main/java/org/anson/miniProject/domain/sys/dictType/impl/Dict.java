package org.anson.miniProject.domain.sys.dictType.impl;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.anson.miniProject.domain.base.BasePO;

@Data
@TableName("dict")
class Dict extends BasePO {
    private String dictTypeId;

    private String no;
    private String name;
    private String dictKey;
    private String description;

    public static final String DICTTYPEID = "dict_type_id";
    public static final String NO = "no";
    public static final String NAME = "name";
    public static final String DICTKEY = "dict_key";
    public static final String DESCRIPTION = "description";

    private static final String _TABLENAME = "dict";

    @Override
    public String TABLENAME() {
        return _TABLENAME;
    }
}
