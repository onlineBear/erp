package org.anson.miniProject.core.model.service.menu;

import lombok.Data;

import java.util.Date;

@Data
public class MenuVo {
    private String id;
    private String no;
    private String icon;
    private String name;
    private String description;
    private String parentMenuId;
    private String clientDictId;
    private String path;
    private Boolean areDisplay;
    private String createUserId;
    private Date createTime;
    private Date lastUpdateTime;
}
