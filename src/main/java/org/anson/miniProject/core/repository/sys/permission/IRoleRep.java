package org.anson.miniProject.core.repository.sys.permission;

import org.anson.miniProject.core.mapper.sys.permission.RoleMapper;
import org.anson.miniProject.core.model.po.sys.permission.Role;
import org.anson.miniProject.core.repository.IBaseRep;

import java.util.Date;

public interface IRoleRep extends IBaseRep<Role, RoleMapper> {
    String insert(Role po, String operUserId, Date operTime) throws Exception;
    void update(Role po, String operUserId, Date operTime) throws Exception;
    void del(String roleId, String operUserId, Date operTime) throws Exception;
}
