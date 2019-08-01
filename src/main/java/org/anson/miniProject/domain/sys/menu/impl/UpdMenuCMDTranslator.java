package org.anson.miniProject.domain.sys.menu.impl;

import org.anson.miniProject.domain.sys.menu.cmd.UpdMenuCMD;
import org.anson.miniProject.tool.bean.BeanUtils;

class UpdMenuCMDTranslator {
    public static Menu toMenu(UpdMenuCMD cmd) throws InstantiationException, IllegalAccessException {
        Menu menu = BeanUtils.beanToBean(cmd, Menu.class);

        return menu;
    }
}
