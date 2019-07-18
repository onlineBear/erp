package org.anson.miniProject.core.repository.sys.permission;

import org.anson.miniProject.core.mapper.sys.permission.RoleResourceMapper;
import org.anson.miniProject.core.model.po.sys.permission.RoleResource;
import org.anson.miniProject.core.repository.IBaseRep;

import java.util.Date;
import java.util.List;

public interface IRoleResourceRep extends IBaseRep<RoleResource, RoleResourceMapper> {
    String insert(RoleResource po, String operUserId, Date operTime) throws Exception;
    void delByRole(String roleId, String operUserId, Date operTime) throws Exception;
    void delByRole(String roleId, List<String> resIdList, String operUserId, Date operTime) throws Exception;
    void delByResource(String resourceId, String operUserId, Date operTime) throws Exception;
    void delByResource(String resourceId, List<String> roleIdList, String operUserId, Date operTime) throws Exception;

    List<String> selResIdListByRole(String roleId);
}
