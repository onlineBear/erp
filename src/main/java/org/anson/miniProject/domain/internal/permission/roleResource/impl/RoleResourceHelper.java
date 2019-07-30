package org.anson.miniProject.domain.internal.permission.roleResource.impl;

import org.anson.miniProject.domain.internal.permission.roleResource.IRoleResourceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional(rollbackFor = Exception.class)
class RoleResourceHelper implements IRoleResourceHelper {
    @Override
    public String addRoleResource(String roleId, String resourceId) throws Exception {
        RoleResource roleResource = new RoleResource();
        roleResource.setRoleId(roleId);
        roleResource.setResourceId(resourceId);
        String id = this.dao.insert(roleResource);
        return id;
    }

    @Override
    public void deleteByRole(String roleId) throws Exception {
        this.dao.deleteByRole(roleId);
    }

    @Override
    public void deleteByRole(String roleId, List<String> resourceIdList) throws Exception {
        this.dao.deleteByRole(roleId, resourceIdList);
    }

    @Override
    public void deleteByResource(String resourceId) throws Exception {
        this.dao.deleteByResource(resourceId);
    }

    @Override
    public void deleteByResource(String resourceId, List<String> roleIdList) throws Exception {
        this.dao.deleteByResource(resourceId, roleIdList);
    }

    // 注入
    @Autowired
    private RoleResourceDao dao;
}
