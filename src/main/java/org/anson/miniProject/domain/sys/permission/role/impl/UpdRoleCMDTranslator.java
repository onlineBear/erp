package org.anson.miniProject.domain.sys.permission.role.impl;

import org.anson.miniProject.domain.sys.permission.role.cmd.UpdRoleCMD;
import org.anson.miniProject.tool.bean.BeanUtils;

class UpdRoleCMDTranslator {
    public static Role toRole(UpdRoleCMD cmd) throws InstantiationException, IllegalAccessException {
        return BeanUtils.beanToBean(cmd, Role.class);
    }
}
