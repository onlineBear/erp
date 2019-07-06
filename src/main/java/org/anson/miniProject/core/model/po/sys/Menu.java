package org.anson.miniProject.core.model.po.sys;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.anson.miniProject.core.model.po.BasePo;

@Data
@TableName("menu")
public class Menu extends BasePo {

    private String no;
    private String icon;
    private String name;
    private String description;
    private String parentMenuId;
    private String clientDictId;
    private String path;
    private Boolean areDisplay;

    public static final String NO = "no";
    public static final String ICON = "icon";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String PARENTMENUID = "parentMenuId";
    public static final String CLIENTDICTID = "clientDictId";
    public static final String PATH = "path";
    public static final String AREDISPLAY = "areDisplay";
    public static final String __TABLENAME = "menu";

}
