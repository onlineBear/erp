package org.anson.miniProject.core.domain.sys.permission.impl;

import org.anson.miniProject.core.domain.sys.permission.IUserRoleDomain;
import org.anson.miniProject.core.model.dmo.sys.permission.userRole.AddUserRoleDMO;
import org.anson.miniProject.core.model.po.sys.permission.UserRole;
import org.anson.miniProject.core.repository.sys.permission.UserRoleRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
@Transactional(rollbackFor = Exception.class)
public class UserRoleDomain implements IUserRoleDomain {
    @Autowired
    private UserRoleRep rep;

    @Override
    public String add(AddUserRoleDMO dmo, String operUserId, Date operTime) throws Exception {
        UserRole po = AddUserRoleDMO.toUserRole(dmo);
        String id = this.rep.insert(po, operUserId, operTime);
        return id;
    }

    @Override
    public void del(String id, String operUserId, Date operTime) throws Exception {
        this.rep.del(id, operUserId, operTime);
    }

    @Override
    public void delByUser(String userId, String operUserId, Date operTime) throws Exception {
        this.rep.delByUser(userId, operUserId, operTime);
    }

    @Override
    public void delByRole(String roleId, String operUserId, Date operTime) throws Exception {
        this.rep.delByRole(roleId, operUserId, operTime);
    }
}
