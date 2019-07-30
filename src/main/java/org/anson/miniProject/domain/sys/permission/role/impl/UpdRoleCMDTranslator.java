package org.anson.miniProject.domain.sys.permission.role.impl;

import org.anson.miniProject.domain.sys.permission.role.cmd.UpdRoleCMD;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

class UpdRoleCMDTranslator {
    private static final BeanCopier toRoleCopier = BeanCopier.create(UpdRoleCMD.class, Role.class, false);

    public static Role toRole(UpdRoleCMD cmd) throws InstantiationException, IllegalAccessException {
        if (cmd == null){
            return null;
        }

        Role role = BeanHelper.beanToBean(cmd, Role.class, toRoleCopier);

        return role;
    }
}
