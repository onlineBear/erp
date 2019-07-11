package org.anson.miniProject.core.domain.sys.permission;

import org.anson.miniProject.core.model.dmo.sys.permission.role.AddRoleDMO;
import org.anson.miniProject.core.model.dmo.sys.permission.role.MdfRoleDMO;

import java.util.Date;

public interface IRoleDomain {
    String add(AddRoleDMO dmo, String operUserId, Date operTime) throws Exception;
    void mdf(MdfRoleDMO dmo, String operUserId, Date operTime) throws Exception;
    void del(String roleId, String operUserId, Date operTime) throws Exception;
}
