package org.anson.miniProject.model.entity.sys;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.anson.miniProject.model.entity.BaseEntity;

@Data
@TableName("dict")
public class Dict extends BaseEntity {
    private String dictTypeId;

    private String no;
    private String name;
    private String dictKey;
    private String description;

    public static final String DICTTYPEID = "dictTypeId";
    public static final String NO = "no";
    public static final String NAME = "name";
    public static final String DICTKEY = "dictKey";
    public static final String DESCRIPTION = "description";

    public static final String __TABLENAME = "dict";
}
