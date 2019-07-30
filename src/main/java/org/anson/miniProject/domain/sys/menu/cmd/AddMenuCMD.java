package org.anson.miniProject.domain.sys.menu.cmd;

import lombok.Data;
import org.anson.miniProject.domain.base.BaseCMD;

import java.util.List;

@Data
public class AddMenuCMD extends BaseCMD {
    private String no;
    private String icon;
    private String name;
    private String description;
    private String parentMenuId;
    private Boolean areDisplay;

    private List<ChildMenu> childMenuList;

    @Data
    public class ChildMenu{
        private String no;
        private String icon;
        private String name;
        private String description;
        private Boolean areDisplay;
    }
}
