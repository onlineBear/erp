package org.zmqy.erp.domain.mis.sys.security.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.zmqy.erp.domain.mis.sys.security.IRoleMenuDomain;
import org.zmqy.erp.domain.mis.sys.security.IUserMenuDomain;
import org.zmqy.erp.mapper.mis.biz.sys.security.RoleMenuMapper;
import org.zmqy.erp.model.mis.bo.sys.security.RoleMenuBo;
import org.zmqy.erp.model.mis.entity.sys.security.RoleMenu;
import org.zmqy.erp.tool.util.common.StringUtil;
import org.zmqy.erp.tool.util.common.dataType.ListUtil;

import java.util.Date;
import java.util.List;

@Component
public class RoleMenuDomain implements IRoleMenuDomain {
    @Autowired
    private RoleMenuMapper mapper;
    @Autowired
    private IUserMenuDomain userMenuDomain;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void mdf(String roleId, List<String> allMenuIdList, String operUserId, Date operTime) throws Exception {
        if(StringUtil.isEmpty(roleId)){
            throw new RuntimeException("请输入角色id");
        }

        // 删除
        QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.notIn(RoleMenu.MENUID, allMenuIdList)
                    .eq(RoleMenu.ROLEID, roleId);

        this.mapper.delete(queryWrapper);

        // saveById
        if(ListUtil.isNotEmpty(allMenuIdList)){
            for(String menuId : allMenuIdList){
                RoleMenuBo bo = new RoleMenuBo();
                bo.setRoleId(roleId);
                bo.setMenuId(menuId);

                this.save(bo, operUserId, operTime);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String add(RoleMenuBo bo, String operUserId, Date operTime) throws Exception{
        if(bo == null){
            throw new RuntimeException("请输入角色菜单");
        }

        RoleMenu roleMenu = RoleMenuBo.bo2entity(bo);

        return this.add(roleMenu, operUserId, operTime);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String save(RoleMenuBo bo, String operUserId, Date operTime) throws Exception{
        if(bo == null){
            throw new RuntimeException("请输入角色菜单");
        }

        RoleMenu roleMenu = RoleMenuBo.bo2entity(bo);

        this.save(roleMenu, operUserId, operTime);

        return roleMenu.getId();
    }

    @Transactional(rollbackFor = Exception.class)
    public void delByRole(String roleId, String operUserId, Date operTime) throws Exception{
        if(StringUtil.isEmpty(roleId)){
            throw new RuntimeException("请输入角色id");
        }

        QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(RoleMenu.ROLEID, roleId);

        this.mapper.delete(queryWrapper);

        // 更新 tUserMenu
        this.userMenuDomain.reBuild(operUserId, operTime);
    }

    // 以下为 类私有方法
    @Transactional(rollbackFor = Exception.class)
    public String add(RoleMenu entity, String operUserId, Date operTime) throws Exception{
        if(StringUtil.isEmpty(entity.getRoleId())){
            throw new RuntimeException("请输入角色id");
        }

        if(StringUtil.isEmpty(entity.getMenuId())){
            throw new RuntimeException("请输入菜单id");
        }

        entity.setId(null);
        entity.setCreateUserId(operUserId);
        entity.setCreateTime(operTime);
        entity.setLastUpdateTime(operTime);

        this.mapper.insert(entity);

        // 更新 tUserMenu
        this.userMenuDomain.reBuild(operUserId, operTime);

        return entity.getId();
    }

    @Transactional(rollbackFor = Exception.class)
    public String save(RoleMenu entity, String operUserId, Date operTime) throws Exception{
        if(entity == null){
            throw new RuntimeException("请输入角色菜单");
        }

        if(StringUtil.isEmpty(entity.getRoleId())){
            throw new RuntimeException("请输入角色id");
        }

        if(StringUtil.isEmpty(entity.getMenuId())){
            throw new RuntimeException("请输入菜单id");
        }

        QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(RoleMenu.ROLEID, entity.getRoleId()).eq(RoleMenu.MENUID, entity.getMenuId());

        RoleMenu roleMenu = this.mapper.selectOne(queryWrapper);

        if(roleMenu == null){
            return this.add(entity, operUserId, operTime);
        }else {
            return roleMenu.getId();
        }
    }
}
