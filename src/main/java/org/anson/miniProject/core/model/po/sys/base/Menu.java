package org.anson.miniProject.core.model.po.sys.base;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.anson.miniProject.core.model.po.BasePO;

@Data
@TableName("menu")
public class Menu extends BasePO {
    private String no;
    private String icon;
    private String name;
    private String description;
    private String parentMenuId;
    private String clientKey;
    private String path;
    private Boolean areDisplay;

    public static final String __TABLENAME = "menu";

    public static final String NO = "no";
    public static final String ICON = "icon";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String PARENTMENUID = "parentMenuId";
    public static final String CLIENTKEY = "clientKey";
    public static final String PATH = "path";
    public static final String AREDISPLAY = "areDisplay";

    @Override
    public String TABLENAME() {
        return __TABLENAME;
    }

}
