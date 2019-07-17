package org.anson.miniProject.core.domain.sys.permission;

import org.anson.miniProject.core.model.dmo.sys.permission.role.AddRoleParam;
import org.anson.miniProject.core.model.dmo.sys.permission.role.MdfRoleParam;

import java.util.Date;

public interface IRoleDomain {
    String add(AddRoleParam dmo, String operUserId, Date operTime) throws Exception;
    void mdf(MdfRoleParam dmo, String operUserId, Date operTime) throws Exception;
    void del(String roleId, String operUserId, Date operTime) throws Exception;
}