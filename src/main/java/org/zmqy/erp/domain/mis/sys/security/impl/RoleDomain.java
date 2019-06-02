package org.zmqy.erp.domain.mis.sys.security.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.zmqy.erp.domain.mis.sys.base.IMenuDomain;
import org.zmqy.erp.domain.mis.sys.security.*;
import org.zmqy.erp.mapper.mis.biz.sys.security.RoleLangMapper;
import org.zmqy.erp.mapper.mis.biz.sys.security.RoleMapper;
import org.zmqy.erp.model.mis.bo.sys.security.*;
import org.zmqy.erp.model.mis.entity.sys.security.Role;
import org.zmqy.erp.model.mis.entity.sys.security.RoleLang;
import org.zmqy.erp.tool.util.common.StringUtil;
import org.zmqy.erp.tool.util.common.dataType.ListUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class RoleDomain implements IRoleDomain {
    @Autowired
    private RoleMapper mapper;
    @Autowired
    private RoleLangMapper roleLangMapper;
    @Autowired
    private IRoleUserDomain roleUserDomain;
    @Autowired
    private IRoleResourceDomain roleResourceDomain;
    @Autowired
    private IRoleMenuDomain roleMenuDomain;
    @Autowired
    private IMenuDomain menuDomain;
    @Autowired
    private IResourceDomain resourceDomain;

    @Override
    public void mdfRolePower(String roleId, List<String> ownUserIdList, List<String> ownMenuResIdList,List<String> ownProgramDraList,
                             String operUserId, Date operTime) throws Exception {
        if(StringUtil.isEmpty(roleId)){
            throw new RuntimeException("请输入角色id");
        }

        // 修改角色下的用户
        this.roleUserDomain.mdf(roleId, ownUserIdList, operUserId, operTime);

        // 修改角色下的菜单
        List<String> ownMenuIdList = new ArrayList<>();

        if(ListUtil.isNotEmpty(ownMenuResIdList)){
            for(String menuId : ownMenuResIdList){
                if(this.menuDomain.existsMenu(menuId)){
                    ownMenuIdList.add(menuId);
                }
            }
        }

        this.roleMenuDomain.mdf(roleId, ownMenuIdList, operUserId, operTime);

        // 修改角色下的资源
        List<String> ownResIdList = new ArrayList<>();

        if(ListUtil.isNotEmpty(ownMenuResIdList)){
            for(String resId : ownMenuResIdList){
                if(this.resourceDomain.existsRes(resId)){
                    ownResIdList.add(resId);
                }
            }
        }

        this.roleResourceDomain.mdf(roleId, ownResIdList, operUserId, operTime);

        //删除流程图资源
        this.roleResourceDomain.mdfProgramDra(roleId, ownProgramDraList,operUserId, operTime);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String add(RoleBo bo, String operUserId, Date operTime) throws Exception{
        if(bo == null){
            throw new RuntimeException("请输入角色");
        }

        Role role = RoleBo.bo2entity(bo);

        this.add(role, operUserId, operTime);

        if(bo.getRoleLangBoList() == null || bo.getRoleLangBoList().size() <= 0){
            throw new RuntimeException("请输入角色名称");
        }

        for(RoleLangBo roleLangBo : bo.getRoleLangBoList()){
            RoleLang roleLang = RoleLangBo.bo2entity(roleLangBo);
            roleLang.setRoleId(role.getId());

            this.add(roleLang, operUserId, operTime);
        }

        if(bo.getRoleUserBoList() != null && bo.getRoleUserBoList().size() > 0){
            for(RoleUserBo roleUserBo : bo.getRoleUserBoList()){
                roleUserBo.setRoleId(role.getId());

                this.roleUserDomain.add(roleUserBo, operUserId, operTime);
            }
        }

        if(bo.getRoleResourceBoList() != null && bo.getRoleResourceBoList().size() > 0){
            for(RoleResourceBo roleResourceBo : bo.getRoleResourceBoList()){
                roleResourceBo.setRoleId(role.getId());

                this.roleResourceDomain.add(roleResourceBo, operUserId, operTime);
            }
        }

        if(bo.getRoleMenuBoList() != null && bo.getRoleMenuBoList().size() > 0){
            for(RoleMenuBo roleMenuBo : bo.getRoleMenuBoList()){
                roleMenuBo.setRoleId(role.getId());

                this.roleMenuDomain.add(roleMenuBo, operUserId, operTime);
            }
        }

        return role.getId();
    }

    @Transactional(rollbackFor = Exception.class)
    public void mdfById(RoleBo bo, String operUserId, Date operTime) throws Exception{
        if(bo == null){
            throw new RuntimeException("请输入角色");
        }

        Role role = RoleBo.bo2entity(bo);

        this.mdfById(role, operUserId, operTime);

        if(bo.getRoleLangBoList() != null && bo.getRoleLangBoList().size() > 0){
            for(RoleLangBo roleLangBo : bo.getRoleLangBoList()){
                RoleLang roleLang = RoleLangBo.bo2entity(roleLangBo);
                roleLang.setRoleId(role.getId());

                this.save(roleLang, operUserId, operTime);
            }
        }

        if(bo.getRoleUserBoList() != null && bo.getRoleUserBoList().size() > 0){
            for(RoleUserBo roleUserBo : bo.getRoleUserBoList()){
                roleUserBo.setRoleId(role.getId());

                this.roleUserDomain.save(roleUserBo, operUserId, operTime);
            }
        }

        if(bo.getRoleResourceBoList() != null && bo.getRoleResourceBoList().size() > 0){
            for(RoleResourceBo roleResourceBo : bo.getRoleResourceBoList()){
                roleResourceBo.setRoleId(role.getId());

                this.roleResourceDomain.save(roleResourceBo, operUserId, operTime);
            }
        }

        if(bo.getRoleMenuBoList() != null && bo.getRoleMenuBoList().size() > 0){
            for(RoleMenuBo roleMenuBo : bo.getRoleMenuBoList()){
                roleMenuBo.setRoleId(role.getId());

                this.roleMenuDomain.save(roleMenuBo, operUserId, operTime);
            }
        }
    }

    // 以下为 类私有方法
    @Transactional(rollbackFor = Exception.class)
    public String add(Role entity, String operUserId, Date operTime) throws Exception{
        if(StringUtil.isEmpty(entity.getRoleNo())){
            throw new RuntimeException("请输入角色编码");
        }

        entity.setCreateUserId(operUserId);
        entity.setCreateTime(operTime);
        entity.setLastUpdateTime(operTime);

        this.mapper.insert(entity);

        return entity.getId();
    }

    @Transactional(rollbackFor = Exception.class)
    public void mdfById(Role entity, String operUserId, Date operTime) throws Exception{
        if(StringUtil.isEmpty(entity.getId())){
            throw new RuntimeException("请输入角色id");
        }

        if(entity.getRoleNo() != null && entity.getRoleNo().trim().equals("")){
            throw new RuntimeException("请输入角色编码");
        }

        entity.setCreateUserId(null);
        entity.setCreateTime(null);
        entity.setLastUpdateTime(operTime);

        this.mapper.updateById(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    public String add(RoleLang entity, String operUserId, Date operTime) throws Exception{
        if(entity == null){
            throw new RuntimeException("请输入角色名称");
        }

        if(StringUtil.isEmpty(entity.getRoleId())){
            throw new RuntimeException("请输入角色id");
        }

        if(StringUtil.isEmpty(entity.getLangId())){
            throw new RuntimeException("请输入语言id");
        }

        if(StringUtil.isEmpty(entity.getRoleName())){
            throw new RuntimeException("请输入角色名称");
        }

        if(StringUtil.isEmpty(entity.getRoleDesc())){
            entity.setRoleDesc("");
        }

        entity.setCreateUserId(operUserId);
        entity.setCreateTime(operTime);
        entity.setLastUpdateTime(operTime);

        this.roleLangMapper.insert(entity);

        return entity.getId();
    }

    @Transactional(rollbackFor = Exception.class)
    public void mdf(RoleLang entity, String operUserId, Date operTime) throws Exception{
        if(entity == null){
            throw new RuntimeException("请输入角色名称");
        }

        if(StringUtil.isEmpty(entity.getRoleId())){
            throw new RuntimeException("请输入角色id");
        }

        if(StringUtil.isEmpty(entity.getLangId())){
            throw new RuntimeException("请输入语言id");
        }

        if(entity.getRoleName() != null && entity.getRoleName().trim().equals("")){
            throw new RuntimeException("请输入角色名称");
        }

        QueryWrapper<RoleLang> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(RoleLang.ROLEID, entity.getRoleId()).eq(RoleLang.LANGID, entity.getLangId());

        entity.setId(null);
        entity.setRoleId(null);
        entity.setLangId(null);
        entity.setCreateUserId(null);
        entity.setCreateTime(null);

        entity.setLastUpdateTime(operTime);

        this.roleLangMapper.update(entity, queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    public String save(RoleLang entity, String operUserId, Date operTime) throws Exception{
        if(entity == null){
            throw new RuntimeException("请输入角色名称");
        }

        if(StringUtil.isEmpty(entity.getRoleId())){
            throw new RuntimeException("请输入角色id");
        }

        if(StringUtil.isEmpty(entity.getLangId())){
            throw new RuntimeException("请输入语言id");
        }

        QueryWrapper<RoleLang> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(RoleLang.ROLEID, entity.getRoleId()).eq(RoleLang.LANGID, entity.getLangId());

        if(this.roleLangMapper.selectCount(queryWrapper) == 0){
            return this.add(entity, operUserId, operTime);
        }else {
            this.mdf(entity, operUserId, operTime);
            return entity.getId();
        }
    }
}
