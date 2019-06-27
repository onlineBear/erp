package org.anson.miniProject.domain.sys.impl;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.anson.miniProject.domain.sys.IMenuDomain;
import org.anson.miniProject.mapper.sys.MenuMapper;
import org.anson.miniProject.model.bo.sys.menu.MenuAddBo;
import org.anson.miniProject.model.bo.sys.menu.MenuMdfBo;
import org.anson.miniProject.model.entity.sys.Menu;
import org.anson.miniProject.tool.helper.InputParamHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
@Transactional(rollbackFor = Exception.class)
public class MenuDomain implements IMenuDomain {
    @Autowired
    private MenuMapper mapper;

    @Override
    public void addMenu(MenuAddBo bo, String operUserId, Date operTime) {
        // 必填检查
        String[] valArray = {bo.getNo(), bo.getParentMenuId()};
        String[] errArray = {"请输入菜单编码", "请选择上级菜单"};
        InputParamHelper.required(valArray, errArray);

        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();

        // 检查编码是否重复
        queryWrapper.eq(Menu.NO, bo.getNo());
        Integer menuCount = this.mapper.selectCount(queryWrapper);

        if(menuCount > 0){
            throw new RuntimeException("已有这个菜单编码, 菜单编码=" + bo.getNo());
        }

        // 检查父级菜单
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Menu.ID, bo.getParentMenuId());
        Menu parentMenu = this.mapper.selectById(queryWrapper);

        if(parentMenu == null){
            throw new RuntimeException("没有这个父级菜单id, 父级菜单id=" + bo.getParentMenuId());
        }

        // 新增
        Menu menu = MenuAddBo.bo2entity(bo);
        menu.setId(menu.getNo());
        menu.setClientDictId(parentMenu.getClientDictId());
        menu.setPath(parentMenu.getPath() + "/" + menu.getId());
        menu.setCreateUserId(operUserId);
        menu.setCreateTime(operTime);
        menu.setLastUpdateTime(operTime);

        this.mapper.insert(menu);
    }

    @Override
    public void mdfMenu(MenuMdfBo bo, String operUserId, Date operTime) {
        if(StrUtil.isEmpty(bo.getId())){
            throw new RuntimeException("MenuMdfBo id 为空");
        }

        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();

        // 检查编码是否重复
        if(StrUtil.isNotEmpty(bo.getNo())){
            queryWrapper.eq(Menu.NO, bo.getNo())
                        .ne(Menu.ID, bo.getId());
            Integer menuCount = this.mapper.selectCount(queryWrapper);

            if(menuCount > 0){
                throw new RuntimeException("已有这个菜单编码, 菜单编码=" + bo.getNo());
            }
        }

        // 检查父级菜单
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Menu.ID, bo.getParentMenuId());
        Menu parentMenu = this.mapper.selectById(queryWrapper);

        if(parentMenu == null){
            throw new RuntimeException("没有这个父级菜单id, 父级菜单id=" + bo.getParentMenuId());
        }

        // 是否显示
        if(bo.getAreDisplay() == null){
            bo.setAreDisplay(true);
        }

        // 修改
        Menu menu = MenuMdfBo.bo2entity(bo);
        menu.setClientDictId(parentMenu.getClientDictId());
        menu.setPath(parentMenu.getPath() + "/" + menu.getId());
        menu.setLastUpdateTime(operTime);

        this.mapper.updateById(menu);

        // 修改 子菜单的 clientDictId 和 path
    }

    @Override
    public void delMenu(String menuId, String operUserId, Date operTime) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();

        queryWrapper.select(Menu.PATH).eq(Menu.ID, menuId);
        Menu menu = this.mapper.selectOne(queryWrapper);

        // 存到删除表

        // 物理删除
        this.mapper.deleteById(menuId);

        // 删除子菜单
        if(menu != null){
            queryWrapper = new QueryWrapper<>();
            queryWrapper.likeRight(Menu.PATH, menu.getPath());
            this.mapper.delete(queryWrapper);
        }
    }

    @Override
    public void delMenu(String[] menuIdArray, String operUserId, Date operTime) {
        if(ArrayUtil.isEmpty(menuIdArray)){
            return;
        }

        for(int i=0;i<menuIdArray.length;i++){
            this.delMenu(menuIdArray[i], operUserId, operTime);
        }
    }
}
