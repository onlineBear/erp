package org.anson.miniProject.domain.sys.permission.role.impl;

import org.anson.miniProject.domain.sys.permission.role.cmd.AddRoleCMD;
import org.anson.miniProject.tool.bean.BeanUtils;

class AddRoleCMDTranslator {
    public static Role toRole(AddRoleCMD cmd) throws InstantiationException, IllegalAccessException {
        Role role = BeanUtils.beanToBean(cmd, Role.class);

        return role;
    }
}
