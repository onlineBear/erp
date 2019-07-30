package org.anson.miniProject.domain.sys.permission.resource.impl;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.anson.miniProject.domain.base.BasePO;

@Data
@TableName("resource")
class Resource extends BasePO {
    private String menuId;

    private String name;
    private String description;
    private String url;

    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String URL = "url";

    private static final String __TABLENAME = "resource";
    @Override
    public String TABLENAME() {
        return __TABLENAME;
    }
}
