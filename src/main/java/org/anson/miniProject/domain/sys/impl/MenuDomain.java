package org.anson.miniProject.domain.sys.impl;

import org.anson.miniProject.domain.sys.IMenuDomain;
import org.anson.miniProject.model.bo.sys.MenuBo;
import org.anson.miniProject.repository.sys.MenuRep;
import org.anson.miniProject.tool.helper.InputParamHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
@Transactional(rollbackFor = Exception.class)
public class MenuDomain implements IMenuDomain {
    @Autowired
    private MenuRep repository;

    @Override
    public String addMenu(MenuBo bo, String operUserId, Date operTime) {
        // 必填检查
        String[] valArray = {bo.getNo(), bo.getParentMenuBo().getId()};
        String[] errArray = {"请输入菜单编码", "请选择上级菜单"};
        InputParamHelper.required(valArray, errArray);

        // 查询父级菜单
        String parentMenuId = bo.getParentMenuBo().getId();
        MenuBo parentMenuBo = this.repository.getMenu(parentMenuId);

        if(parentMenuBo == null){
            throw new RuntimeException("没有这个父级菜单, 父级菜单id = " + parentMenuId);
        }

        bo.setParentMenuBo(parentMenuBo);

        // 新增
        bo.setId(bo.getNo());   // id 和 no 保持一致
        bo.setClientDictId(parentMenuBo.getClientDictId()); // 客户端id 和 父级一致
        bo.setPath(this.calPath(parentMenuBo.getPath(), bo.getId()));   // path = 父级path + 本节点id

        String menuId = this.repository.addMenu(bo, operUserId, operTime);

        return menuId;
    }

    @Override
    public void mdfMenu(MenuBo bo, String operUserId, Date operTime) {
        // 必填检查
        String[] valArray = {bo.getId()};
        String[] errArray = {"请输入菜单id"};
        InputParamHelper.required(valArray, errArray);

        // 修改
        this.repository.mdfMenu(bo, operUserId, operTime);
    }

    @Override
    public void delMenu(String menuId) {
        this.repository.delMenu(menuId);
    }

    @Override
    public void delMenu(String[] menuIdArray) {
        this.repository.delMenu(menuIdArray);
    }

    private String calPath(String parentMenuPath, String menuId){
        StringBuilder sb = new StringBuilder();
        sb.append(parentMenuPath).append("/").append(menuId);
        return sb.toString();
    }
}
