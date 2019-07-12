package org.anson.miniProject.core.domain.sys.permission.impl;

import org.anson.miniProject.core.domain.sys.permission.IRoleResourceDomain;
import org.anson.miniProject.core.model.dmo.sys.permission.roleResource.AddRoleResourceDMO;
import org.anson.miniProject.core.model.po.sys.permission.RoleResource;
import org.anson.miniProject.core.repository.sys.permission.RoleResourceRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
@Transactional(rollbackFor = Exception.class)
public class RoleResourceDomain implements IRoleResourceDomain {
    @Autowired
    private RoleResourceRep rep;

    @Override
    public String add(AddRoleResourceDMO dmo, String operUserId, Date operTime) throws Exception {
        RoleResource po = AddRoleResourceDMO.toRoleResource(dmo);
        String id = this.rep.insert(po, operUserId, operTime);
        return id;
    }

    @Override
    public void del(String id, String operUserId, Date operTime) throws Exception {
        this.rep.del(id, operUserId, operTime);
    }

    @Override
    public void delByResource(String resId, String operUserId, Date operTime) throws Exception {
        this.rep.delByResource(resId, operUserId, operTime);
    }

    @Override
    public void delByRole(String roleId, String operUserId, Date operTime) throws Exception {
        this.rep.delByRole(roleId, operUserId, operTime);
    }
}
