package org.zmqy.erp.domain.mis.sys.security.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.zmqy.erp.domain.mis.sys.security.IUserMenuDomain;
import org.zmqy.erp.mapper.mis.biz.sys.security.UserMenuMapper;
import org.zmqy.erp.model.mis.entity.sys.security.UserMenu;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class UserMenuDomain implements IUserMenuDomain {
    @Autowired
    private UserMenuMapper mapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void reBuild(String operUserId, Date operTime) throws Exception{
        // 删除全部的 tUserMenu
        this.mapper.delete(null);

        // 查出最新的
        List<Map> recentUserMenuList = this.mapper.geRecentUserMenu();

        // 插入
        List<UserMenu> userMenuList = new ArrayList<>();

        if(recentUserMenuList != null && recentUserMenuList.size() > 0){
            for(Map map : recentUserMenuList){
                UserMenu userMenu = new UserMenu();
                userMenu.setMenuId((String) map.get("menuId"));
                userMenu.setUserId((String) map.get("userId"));
                //userMenu.setId(IdHelper.nextId());
                userMenu.setCreateUserId(operUserId);
                userMenu.setCreateTime(operTime);
                userMenu.setLastUpdateTime(operTime);

                userMenuList.add(userMenu);
            }
        }

        if(userMenuList != null && userMenuList.size() > 0){
            this.mapper.batchInsert(userMenuList);
        }
    }
}
