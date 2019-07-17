package org.anson.miniProject.core.domain.sys.permission.impl;

import cn.hutool.core.collection.IterUtil;
import lombok.extern.slf4j.Slf4j;
import org.anson.miniProject.core.domain.sys.permission.IRoleDomain;
import org.anson.miniProject.core.domain.sys.permission.IRoleResourceDomain;
import org.anson.miniProject.core.domain.sys.permission.IUserRoleDomain;
import org.anson.miniProject.core.model.dmo.sys.permission.role.AddRoleParam;
import org.anson.miniProject.core.model.dmo.sys.permission.role.MdfRoleParam;
import org.anson.miniProject.core.model.bo.sys.permission.role.RoleBO;
import org.anson.miniProject.core.model.po.sys.permission.Role;
import org.anson.miniProject.core.repository.sys.permission.impl.RoleRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class RoleDomain implements IRoleDomain{
    @Autowired
    private RoleRep rep;
    @Autowired
    private IUserRoleDomain userRoleDomain;
    @Autowired
    private IRoleResourceDomain roleResourceDomain;

    @Override
    public String add(AddRoleParam addParam, String operUserId, Date operTime) throws Exception {
        RoleBO bo = AddRoleParam.toBO(addParam);

        Role po = RoleBO.toRole(bo);

        // 新增角色
        String roleId = this.rep.insert(po, operUserId, operTime);

        // 新增角色和用户的关系
        this.userRoleDomain.addByRole(roleId, bo.getUserIdList(), operUserId, operTime);

        // 新增角色和资源的关系
        this.roleResourceDomain.addByRole(roleId, bo.getResourceIdList(), operUserId, operTime);

        return roleId;
    }

    @Override
    public void mdf(MdfRoleParam dmo, String operUserId, Date operTime) throws Exception {
        RoleBO bo = MdfRoleParam.toBO(dmo);

        Role po = RoleBO.toRole(bo);

        this.rep.update(po, operTime);

        // 用户角色关系
        if (IterUtil.isEmpty(dmo.getLeftUserIdList())){
            this.userRoleDomain.delByRole(po.getId(), operUserId, operTime);
        }else {
            List<String> userIdList = this.userRoleDomain.getUserIdListByRole(po.getId());

            if(IterUtil.isNotEmpty(userIdList)){
                List<String> removeUserIdList = new ArrayList<>(userIdList);
                removeUserIdList.removeAll(dmo.getLeftUserIdList());
                this.userRoleDomain.delByRole(po.getId(), removeUserIdList, operUserId, operTime);
            }

            List<String> addUserIdList = new ArrayList<>(dmo.getLeftUserIdList());
            addUserIdList.removeAll(userIdList);

            this.userRoleDomain.addByUser(po.getId(), addUserIdList, operUserId, operTime);
        }

        // 角色资源关系
        if (IterUtil.isEmpty(dmo.getLeftResIdList())){
            this.roleResourceDomain.delByRole(po.getId(), operUserId, operTime);
        }else {
            List<String> resIdList = this.roleResourceDomain.getResIdListByRole(po.getId());

            if(IterUtil.isNotEmpty(resIdList)){
                List<String> removeResIdList = new ArrayList<>(resIdList);
                removeResIdList.removeAll(dmo.getLeftResIdList());
                this.roleResourceDomain.delByRole(po.getId(), removeResIdList, operUserId, operTime);
            }

            List<String> addResIdList = new ArrayList<>(dmo.getLeftResIdList());
            addResIdList.removeAll(resIdList);

            this.roleResourceDomain.addByRole(po.getId(), addResIdList, operUserId, operTime);
        }
    }

    @Override
    public void del(String roleId, String operUserId, Date operTime) throws Exception {
        // 删除角色
        this.rep.del(roleId, operUserId, operTime);

        // 删除 用户角色关系表
        this.userRoleDomain.delByRole(roleId, operUserId, operTime);

        // 删除 角色资源关系表
        this.roleResourceDomain.delByRole(roleId, operUserId, operTime);
    }
}
