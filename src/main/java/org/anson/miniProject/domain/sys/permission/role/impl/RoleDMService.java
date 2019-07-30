package org.anson.miniProject.domain.sys.permission.role.impl;

import org.anson.miniProject.domain.internal.roleResource.impl.RoleResourceDao;
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
        this.roleResourceDao.deleteByRole(id);
        // 删除 角色用户
    }

    // 注入
    @Autowired
    private RoleDao dao;
    @Autowired
    private RoleResourceDao roleResourceDao;
}
