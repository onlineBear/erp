package org.anson.miniProject.model.entity.sys;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.anson.miniProject.model.entity.BaseEntity;

@Data
@TableName("dictType")
public class DictType extends BaseEntity {
    private String no;
    private String name;
    private String description;

    public static final String NO = "no";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";

    public static final String __TABLENAME = "dictType";
}
