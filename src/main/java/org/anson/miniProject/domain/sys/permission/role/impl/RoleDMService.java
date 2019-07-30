package org.anson.miniProject.domain.sys.permission.role.impl;

import org.anson.miniProject.domain.internal.permission.roleResource.IRoleResourceHelper;
import org.anson.miniProject.domain.internal.permission.userRole.IUserRoleHelper;
import org.anson.miniProject.domain.sys.permission.role.IRoleDMService;
import org.anson.miniProject.domain.sys.permission.role.cmd.AddRoleCMD;
import org.anson.miniProject.domain.sys.permission.role.cmd.UpdRoleCMD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(rollbackFor = Exception.class)
public class RoleDMService implements IRoleDMService {
    @Override
    public String addRole(AddRoleCMD cmd) throws Exception {
        // 检查 cmd

        Role role = AddRoleCMDTranslator.toRole(cmd);

        String id = this.dao.insert(role);

        return id;
    }

    @Override
    public void updateRole(UpdRoleCMD cmd) throws Exception {
        // 检查 cmd

        Role role = UpdRoleCMDTranslator.toRole(cmd);

        this.dao.updateById(role);
    }

    @Override
    public void deleteRole(String id) throws Exception {
        // 删除 role
        this.dao.deleteById(id);
        // 删除 角色资源
        this.roleResourceHelper.deleteByRole(id);
        // 删除 角色用户
        this.userRoleHelper.deleteByRole(id);
    }

    // 注入
    @Autowired
    private RoleDao dao;
    @Autowired
    private IRoleResourceHelper roleResourceHelper;
    @Autowired
    private IUserRoleHelper userRoleHelper;
}
