package org.anson.miniProject.core.domain.sys.permission;

import org.anson.miniProject.core.model.dmo.sys.permission.roleResource.AddRoleResourceDMO;

import java.util.Date;

public interface IRoleResourceDomain {
    String add(AddRoleResourceDMO dmo, String operUserId, Date operTime) throws Exception;
    void del(String id, String operUserId, Date operTime) throws Exception;
    void delByResource(String resId, String operUserId, Date operTime) throws Exception;
    void delByRole(String roleId, String operUserId, Date operTime) throws Exception;
}
