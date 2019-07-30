package org.anson.miniProject.domain.sys.menu.impl;

import cn.hutool.core.collection.CollUtil;
import org.anson.miniProject.domain.sys.menu.cmd.AddMenuCMD;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

import java.util.ArrayList;
import java.util.List;

class AddMenuCMDTranslator {
    private static final BeanCopier toMenuCopier = BeanCopier.create(AddMenuCMD.class, Menu.class, false);

    public static Menu toMenu(AddMenuCMD cmd) throws InstantiationException, IllegalAccessException {
        if (cmd == null){
            return null;
        }

        Menu menu = BeanHelper.beanToBean(cmd, Menu.class, toMenuCopier);

        return menu;
    }

    public static List<Menu> toChildMenuList(AddMenuCMD cmd) throws InstantiationException, IllegalAccessException {
        if (cmd == null || CollUtil.isEmpty(cmd.getChildMenuList())){
            return null;
        }

        List<Menu> childMenuList = new ArrayList<>();

        for (AddMenuCMD.ChildMenu one : cmd.getChildMenuList()){
            Menu childMenu = BeanHelper.beanToBean(one, Menu.class, toMenuCopier);
            childMenuList.add(childMenu);
        }

        return childMenuList;
    }
}
