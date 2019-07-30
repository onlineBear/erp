package org.anson.miniProject.domain.sys.permission.role.cmd;

import org.anson.miniProject.domain.base.BaseCMD;

import java.util.List;

public class UpdRoleCMD extends BaseCMD {
    private String id;

    private String parentRoleId;

    private String no;
    private String name;
    private String description;

    private List<String> leftUserIdList;

    private List<String> leftResIdList;
}
