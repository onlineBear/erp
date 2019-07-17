package org.anson.miniProject.core.model.po.sys.permission;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.anson.miniProject.core.model.po.BasePO;

@Data
@TableName("resource")
public class Resource extends BasePO {
    private String menuId;

    private String name;
    private String description;
    private String url;

    /**
     * 表名
     */
    public static final String __TABLENAME = "resource";

    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String URL = "url";

    @Override
    public String TABLENAME() {
        return __TABLENAME;
    }
}
