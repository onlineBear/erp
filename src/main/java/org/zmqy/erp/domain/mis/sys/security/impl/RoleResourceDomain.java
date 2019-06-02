package org.zmqy.erp.domain.mis.sys.security.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.zmqy.erp.domain.mis.sys.security.IRoleResourceDomain;
import org.zmqy.erp.domain.mis.sys.security.IUserResourceDomain;
import org.zmqy.erp.mapper.mis.biz.sys.security.RoleProgramDraMapper;
import org.zmqy.erp.mapper.mis.biz.sys.security.RoleResourceMapper;
import org.zmqy.erp.model.mis.bo.sys.security.RoleProgramDraBo;
import org.zmqy.erp.model.mis.bo.sys.security.RoleResourceBo;
import org.zmqy.erp.model.mis.entity.sys.security.RoleProgramDra;
import org.zmqy.erp.model.mis.entity.sys.security.RoleResource;
import org.zmqy.erp.tool.util.common.StringUtil;
import org.zmqy.erp.tool.util.common.dataType.ListUtil;

import java.util.Date;
import java.util.List;

@Component
public class RoleResourceDomain implements IRoleResourceDomain {
    @Autowired
    private RoleResourceMapper mapper;
    @Autowired
    private IUserResourceDomain userResourceDomain;
    @Autowired
    private RoleProgramDraMapper roleProgramDraMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void mdf(String roleId, List<String> allResIdList, String operUserId, Date operTime) throws Exception {
        if(StringUtil.isEmpty(roleId)){
            throw new RuntimeException("请输人角色id");
        }

        // 全删除
        this.delByRole(roleId, operUserId, operTime);

        // 新增
        if(ListUtil.isNotEmpty(allResIdList)){
            for(String resId : allResIdList){
                RoleResourceBo bo = new RoleResourceBo();
                bo.setRoleId(roleId);
                bo.setResId(resId);

                this.save(bo, operUserId, operTime);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void mdfProgramDra(String roleId, List<String> ownProgramDraList, String operUserId, Date operTime) throws Exception {
        if(StringUtil.isEmpty(roleId)){
            throw new RuntimeException("请输人角色id");
        }

        // 全删除
        this.delByRoleProgramDra(roleId, operUserId, operTime);

        // 新增
        if(ListUtil.isNotEmpty(ownProgramDraList)){
            for(String programDraId : ownProgramDraList){
                RoleProgramDraBo roleProgramDraBo = new RoleProgramDraBo();
                roleProgramDraBo.setRoleId(roleId);
                roleProgramDraBo.setProgramDraId(programDraId);
                roleProgramDraBo.setCreateUserId(operUserId);
                roleProgramDraBo.setCreateUserTime(operTime);
                roleProgramDraBo.setLastUpdateTime(operTime);
                this.add(roleProgramDraBo,operUserId, operTime);

            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void delByRoleProgramDra(String roleId, String operUserId, Date operTime){
        QueryWrapper<RoleProgramDra> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(RoleProgramDra.ROLEID,roleId);
        this.roleProgramDraMapper.delete(queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    public String add(RoleProgramDraBo bo, String operUserId, Date operTime) throws Exception{
        if(bo == null){
            throw new RuntimeException("请输入流程图资源");
        }

        RoleProgramDra roleProgramDra = RoleProgramDraBo.bo2entity(bo);
        this.roleProgramDraMapper.insert(roleProgramDra);

        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String add(RoleResourceBo bo, String operUserId, Date operTime) throws Exception{
        if(bo == null){
            throw new RuntimeException("请输入角色资源");
        }

        RoleResource roleResource = RoleResourceBo.bo2entity(bo);

        return this.add(roleResource, operUserId, operTime);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String save(RoleResourceBo bo, String operUserId, Date operTime) throws Exception{
        if(bo == null){
            throw new RuntimeException("请输入角色资源");
        }

        RoleResource roleResource = RoleResourceBo.bo2entity(bo);

        return this.save(roleResource, operUserId, operTime);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delByRole(String roleId, String operUserId, Date operTime) throws Exception{
        if(StringUtil.isEmpty(roleId)){
            throw new RuntimeException("请输入角色id");
        }

        QueryWrapper<RoleResource> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(RoleResource.ROLEID, roleId);

        this.mapper.delete(queryWrapper);

        // 更新 tUserResource
        this.userResourceDomain.reBuild(operUserId, operTime);
    }

    // 以下为 类私有方法
    @Transactional(rollbackFor = Exception.class)
    public String add(RoleResource entity, String operUserId, Date operTime) throws Exception{
        if(StringUtil.isEmpty(entity.getRoleId())){
            throw new RuntimeException("请输入角色id");
        }

        if(StringUtil.isEmpty(entity.getResId())){
            throw new RuntimeException("请输入资源id");
        }

        entity.setId(null);
        entity.setCreateUserId(operUserId);
        entity.setCreateTime(operTime);
        entity.setLastUpdateTime(operTime);

        this.mapper.insert(entity);

        // 更新 tUserResource
        this.userResourceDomain.reBuild(operUserId, operTime);

        return entity.getId();
    }

    @Transactional(rollbackFor = Exception.class)
    public String save(RoleResource entity, String operUserId, Date operTime) throws Exception{
        if(StringUtil.isEmpty(entity.getRoleId())){
            throw new RuntimeException("请输入角色id");
        }

        if(StringUtil.isEmpty(entity.getResId())){
            throw new RuntimeException("请输入资源id");
        }

        QueryWrapper<RoleResource> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(RoleResource.ROLEID, entity.getRoleId()).eq(RoleResource.RESID, entity.getResId());

        RoleResource one = this.mapper.selectOne(queryWrapper);

        if(one == null){
            return this.add(entity, operUserId, operTime);
        }else{
            return one.getId();
        }
    }
}
