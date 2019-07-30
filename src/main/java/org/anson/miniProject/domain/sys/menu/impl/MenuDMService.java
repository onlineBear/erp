package org.anson.miniProject.domain.sys.menu.impl;

import cn.hutool.core.collection.CollUtil;
import org.anson.miniProject.domain.sys.menu.IMenuDMService;
import org.anson.miniProject.domain.sys.menu.cmd.AddMenuCMD;
import org.anson.miniProject.domain.sys.menu.cmd.UpdMenuCMD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional(rollbackFor = Exception.class)
class MenuDMService implements IMenuDMService {
    @Override
    public String addMenu(AddMenuCMD cmd) throws Exception {
        // 检查 cmd
        Menu menu = AddMenuCMDTranslator.toMenu(cmd);

        if (menu == null){
            return null;
        }

        // 新增菜单


        // 新增子菜单
        List<Menu> childMenuList = AddMenuCMDTranslator.toChildMenuList(cmd);

        if (CollUtil.isNotEmpty(childMenuList)){
            for (Menu childMenu : childMenuList){
                // 检查编码等
            }
        }

        String id = this.dao.insert(menu);

        return id;
    }

    @Override
    public void updateMenu(UpdMenuCMD cmd) throws Exception {
        // 检查 cmd

        // 检查 菜单编码等

        // 修改菜单

        // 若修改了父级菜单, 则相应修改子菜单
    }

    // 注入
    @Autowired
    private MenuDao dao;
}
