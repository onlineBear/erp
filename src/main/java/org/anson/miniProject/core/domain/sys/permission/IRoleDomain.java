package org.anson.miniProject.core.domain.sys.permission;

import org.anson.miniProject.core.model.param.sys.permission.role.AddRoleParam;
import org.anson.miniProject.core.model.param.sys.permission.role.MdfRoleParam;

import java.util.Date;

public interface IRoleDomain {
    String add(AddRoleParam param, String operUserId, Date operTime) throws Exception;
    void mdf(MdfRoleParam param, String operUserId, Date operTime) throws Exception;
    void del(String roleId, String operUserId, Date operTime) throws Exception;
}
