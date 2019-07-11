package org.anson.miniProject.core.domain.sys.permission;

import org.anson.miniProject.core.model.dmo.sys.permission.userRole.AddUserRoleDMO;

import java.util.Date;

public interface IUserRoleDomain {
    String add(AddUserRoleDMO dmo, String operUserId, Date operTime) throws Exception;
    void del(String id, String operUserId, Date operTime) throws Exception;
    void delByUser(String userId, String operUserId, Date operTime) throws Exception;
    void delByRole(String roleId, String operUserId, Date operTime) throws Exception;
}
