package org.anson.miniProject.core.biz.sys.permission;

import org.anson.miniProject.core.domain.sys.permission.IRoleDomain;
import org.anson.miniProject.core.model.bo.sys.permission.role.AddRoleBO;
import org.anson.miniProject.core.model.bo.sys.permission.role.MdfRoleBO;
import org.anson.miniProject.core.model.dmo.sys.permission.role.AddRoleDMO;
import org.anson.miniProject.core.model.dmo.sys.permission.role.MdfRoleDMO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
@Transactional(rollbackFor = Exception.class)
public class RoleBiz {
    @Autowired
    private IRoleDomain domain;

    public String add(AddRoleBO bo, String operUserId, Date operTime) throws Exception{
        AddRoleDMO dmo = AddRoleBO.toAddRoleDMO(bo);
        return this.domain.add(dmo, operUserId, operTime);
    }

    public void mdf(MdfRoleBO bo, String operUserId, Date operTime) throws Exception{
        MdfRoleDMO dmo = MdfRoleBO.toAddRoleDMO(bo);
        this.domain.mdf(dmo, operUserId, operTime);
    }

    public void del(String roleId, String operUserId, Date operTime) throws Exception{
        this.domain.del(roleId, operUserId, operTime);
    }
}
