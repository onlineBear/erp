package org.anson.miniProject.domain.sys.menu.impl;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.anson.miniProject.domain.base.BasePO;

@Data
@TableName("menu")
class Menu extends BasePO {
    private String no;
    private String icon;
    private String name;
    private String description;
    private String parentMenuId;
    private String clientKey;
    private String path;
    private Boolean areDisplay;

    public static final String NO = "no";
    public static final String ICON = "icon";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String PARENTMENUID = "parentMenuId";
    public static final String CLIENTKEY = "clientKey";
    public static final String PATH = "path";
    public static final String AREDISPLAY = "areDisplay";

    private static final String __TABLENAME = "menu";

    @Override
    public String TABLENAME() {
        return null;
    }
}
