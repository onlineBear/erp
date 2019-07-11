package org.anson.miniProject.core.domain.sys.permission.impl;

import org.anson.miniProject.core.domain.sys.IDelRecordDomain;
import org.anson.miniProject.core.domain.sys.permission.IRoleDomain;
import org.anson.miniProject.core.model.dmo.sys.DelRecordDmo;
import org.anson.miniProject.core.model.dmo.sys.permission.role.AddRoleDMO;
import org.anson.miniProject.core.model.dmo.sys.permission.role.MdfRoleDMO;
import org.anson.miniProject.core.model.po.sys.permission.Role;
import org.anson.miniProject.core.repository.sys.permission.RoleRep;
import org.anson.miniProject.framework.jackson.Jackson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
@Transactional(rollbackFor = Exception.class)
public class RoleDomain implements IRoleDomain{
    @Autowired
    private RoleRep rep;
    @Autowired
    private IDelRecordDomain delRecordDomain;
    @Autowired
    private Jackson jackson;

    @Override
    public String add(AddRoleDMO dmo, String operUserId, Date operTime) throws Exception {
        Role po = AddRoleDMO.toRole(dmo);

        return this.rep.insert(po, operUserId, operTime);
    }

    @Override
    public void mdf(MdfRoleDMO dmo, String operUserId, Date operTime) throws Exception {
        Role po = MdfRoleDMO.toRole(dmo);

        this.rep.update(po, operTime);
    }

    @Override
    public void del(String roleId, String operUserId, Date operTime) throws Exception {
        // 记录原记录
        Role po = this.rep.selectById(roleId);

        if (po == null){
            return;
        }

        DelRecordDmo delRecordDmo = new DelRecordDmo(Role.__TABLENAME, roleId, jackson.toJson(po));
        this.delRecordDomain.record(delRecordDmo, operUserId, operTime);

        // 删除
        this.rep.del(roleId);
    }
}
