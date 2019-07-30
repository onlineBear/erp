package org.anson.miniProject.domain.sys.menu.impl;

import org.anson.miniProject.domain.sys.menu.cmd.UpdMenuCMD;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

class UpdMenuCMDTranslator {
    private static final BeanCopier toMenuCopier = BeanCopier.create(UpdMenuCMD.class, Menu.class, false);

    public static Menu toMenu(UpdMenuCMD cmd) throws InstantiationException, IllegalAccessException {
        if (cmd == null){
            return null;
        }

        Menu menu = BeanHelper.beanToBean(cmd, Menu.class, toMenuCopier);

        return menu;
    }
}
