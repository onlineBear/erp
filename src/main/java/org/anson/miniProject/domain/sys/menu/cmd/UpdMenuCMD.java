package org.anson.miniProject.domain.sys.menu.cmd;

import lombok.Data;

@Data
public class UpdMenuCMD {
    private String id;
    private String icon;
    private String name;
    private String description;
    private String parentMenuId;
    private Boolean areDisplay;
}
