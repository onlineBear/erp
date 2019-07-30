package org.anson.miniProject.domain.internal.permission.userRole.impl;

import org.anson.miniProject.domain.internal.permission.userRole.IUserRoleHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional(rollbackFor = Exception.class)
class UserRoleHelper implements IUserRoleHelper {
    @Override
    public String addUserRole(String userId, String roleId) throws Exception {
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        userRole.setRoleId(roleId);

        String id = this.dao.insert(userRole);
        return id;
    }

    @Override
    public void deleteByUser(String userId) throws Exception {
        this.dao.deleteByUser(userId);
    }

    @Override
    public void deleteByUser(String userId, List<String> roleIdList) throws Exception {
        this.dao.deleteByUser(userId, roleIdList);
    }

    @Override
    public void deleteByRole(String roleId) throws Exception {
        this.dao.deleteByRole(roleId);
    }

    @Override
    public void deleteByRole(String roleId, List<String> userIdList) throws Exception {
        this.dao.deleteByRole(roleId, userIdList);
    }

    // 注入
    @Autowired
    private UserRoleDao dao;
}
