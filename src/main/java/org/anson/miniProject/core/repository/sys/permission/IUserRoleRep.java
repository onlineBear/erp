package org.anson.miniProject.core.repository.sys.permission;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.anson.miniProject.core.mapper.sys.permission.UserRoleMapper;
import org.anson.miniProject.core.model.po.sys.permission.UserRole;
import org.anson.miniProject.core.repository.IBaseRep;

import java.util.Date;
import java.util.List;

public interface IUserRoleRep extends IBaseRep<UserRole, UserRoleMapper> {
    String insert(UserRole po, String operUserId, Date operTime);

    void del(String userId, String roleId, String operUserId, Date operTime) throws JsonProcessingException;

    void delByUser(String userId, String operUserId, Date operTime) throws JsonProcessingException;

    void delByUser(String userId, List<String> roleIdList, String operUserId, Date operTime) throws JsonProcessingException;

    void delByRole(String roleId, String operUserId, Date operTime) throws JsonProcessingException;

    void delByRole(String roleId, List<String> userIdList, String operUserId, Date operTime) throws JsonProcessingException;
}
