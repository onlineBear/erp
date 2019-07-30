package org.anson.miniProject.domain.sys.permission.role.cmd;

import org.anson.miniProject.domain.base.BaseCMD;

import java.util.List;

public class AddRoleCMD extends BaseCMD {
    private String parentRoleId;

    private String no;
    private String name;
    private String description;

    private List<String> resourceIdList;
    private List<String> userIdList;
}
