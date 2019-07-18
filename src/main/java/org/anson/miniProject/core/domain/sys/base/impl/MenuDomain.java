package org.anson.miniProject.core.domain.sys.base.impl;

import org.anson.miniProject.core.domain.sys.base.IMenuDomain;
import org.anson.miniProject.core.model.bo.sys.base.MenuBO;
import org.anson.miniProject.core.model.param.sys.base.menu.AddMenuParam;
import org.anson.miniProject.core.model.param.sys.base.menu.MdfMenuParam;
import org.anson.miniProject.core.model.po.sys.base.Menu;
import org.anson.miniProject.core.repository.sys.base.impl.MenuRep;
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
    public String addMenu(AddMenuParam param, String operUserId, Date operTime) throws Exception{
        MenuBO bo = param.toBO();

        Menu po = bo.toMenu();

        // 必填检查
        String[] valArray = {po.getNo(), po.getParentMenuId()};
        String[] errArray = {"请输入菜单编码", "请选择上级菜单"};
        InputParamHelper.required(valArray, errArray);

        // 查询父级菜单
        String parentMenuId = bo.getParentMenuBO().getId();
        Menu parentMenu = this.rep.selectById(parentMenuId);

        if(parentMenu == null){
            throw new RuntimeException("没有这个父级菜单, 父级菜单id = " + parentMenuId);
        }

        // 新增
        po.setId(po.getNo());   // id 和 userNo 保持一致
        po.setClientDictId(parentMenu.getClientDictId()); // 客户端id 和 父级一致
        po.setPath(this.calPath(parentMenu.getPath(), po.getId()));   // path = 父级path + 本节点id

        String menuId = this.rep.addMenu(po, operUserId, operTime);

        return menuId;
    }

    @Override
    public void mdfMenu(MdfMenuParam param, String operUserId, Date operTime) throws Exception{
        MenuBO bo = param.toBO();

        Menu po = bo.toMenu();

        // 必填检查
        String[] valArray = {po.getId()};
        String[] errArray = {"请输入菜单id"};
        InputParamHelper.required(valArray, errArray);

        // 修改
        this.rep.mdfMenu(po, operUserId, operTime);
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
