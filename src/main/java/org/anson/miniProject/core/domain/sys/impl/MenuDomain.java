package org.anson.miniProject.core.domain.sys.impl;

import org.anson.miniProject.core.domain.sys.IMenuDomain;
import org.anson.miniProject.core.model.dmo.sys.MenuDmo;
import org.anson.miniProject.core.repository.sys.MenuRep;
import org.anson.miniProject.tool.helper.InputParamHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
@Transactional(rollbackFor = Exception.class)
public class MenuDomain implements IMenuDomain {
    @Autowired
    private MenuRep rep;

    @Override
    public String addMenu(MenuDmo bo, String operUserId, Date operTime) throws Exception{
        // 必填检查
        String[] valArray = {bo.getNo(), bo.getParentMenuDmo().getId()};
        String[] errArray = {"请输入菜单编码", "请选择上级菜单"};
        InputParamHelper.required(valArray, errArray);

        // 查询父级菜单
        String parentMenuId = bo.getParentMenuDmo().getId();
        MenuDmo parentMenuDmo = this.rep.getMenu(parentMenuId);

        if(parentMenuDmo == null){
            throw new RuntimeException("没有这个父级菜单, 父级菜单id = " + parentMenuId);
        }

        bo.setParentMenuDmo(parentMenuDmo);

        // 新增
        bo.setId(bo.getNo());   // id 和 no 保持一致
        bo.setClientDictId(parentMenuDmo.getClientDictId()); // 客户端id 和 父级一致
        bo.setPath(this.calPath(parentMenuDmo.getPath(), bo.getId()));   // path = 父级path + 本节点id

        String menuId = this.rep.addMenu(bo, operUserId, operTime);

        return menuId;
    }

    @Override
    public void mdfMenu(MenuDmo bo, String operUserId, Date operTime) throws Exception{
        // 必填检查
        String[] valArray = {bo.getId()};
        String[] errArray = {"请输入菜单id"};
        InputParamHelper.required(valArray, errArray);

        // 修改
        this.rep.mdfMenu(bo, operUserId, operTime);
    }

    @Override
    public void delMenu(String menuId, String operUserId, Date operTime) throws Exception {
        this.rep.delMenu(menuId, operUserId, operTime);

        // 删除子菜单

    }

    private String calPath(String parentMenuPath, String menuId){
        StringBuilder sb = new StringBuilder();
        sb.append(parentMenuPath).append("/").append(menuId);
        return sb.toString();
    }
}
