package org.anson.miniProject.domain.internal.permission.userRole;

import java.util.List;

public interface IUserRoleHelper {
    String addUserRole(String userId, String roleId) throws Exception;
    void deleteByUser(String userId) throws Exception;
    void deleteByUser(String userId, List<String> roleIdList) throws Exception;
    void deleteByRole(String roleId) throws Exception;
    void deleteByRole(String roleId, List<String> userIdList) throws Exception;
}
