package org.anson.miniProject.core.model.service.menu;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class MenuMdfDTO {
    @NotEmpty(message = "请输入菜单id")
    private String id;
    private String no;
    private String icon;
    private String name;
    private String description;
    private String parentMenuId;
    private Boolean areDisplay;
}
