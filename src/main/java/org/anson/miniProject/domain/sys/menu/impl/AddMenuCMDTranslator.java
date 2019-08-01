package org.anson.miniProject.domain.sys.menu.impl;

import cn.hutool.core.collection.CollUtil;
import org.anson.miniProject.domain.sys.menu.cmd.AddMenuCMD;
import org.anson.miniProject.tool.bean.BeanUtils;

import java.util.List;

class AddMenuCMDTranslator {
    public static Menu toMenu(AddMenuCMD cmd) throws InstantiationException, IllegalAccessException {
        return BeanUtils.beanToBean(cmd, Menu.class);
    }

    public static List<Menu> toChildMenuList(AddMenuCMD cmd) throws InstantiationException, IllegalAccessException {
        if (cmd == null || CollUtil.isEmpty(cmd.getChildMenuList())){
            return null;
        }

        return BeanUtils.beansToBeans(cmd.getChildMenuList(), Menu.class);
    }
}
