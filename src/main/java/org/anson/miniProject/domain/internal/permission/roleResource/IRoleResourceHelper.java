package org.anson.miniProject.domain.internal.permission.roleResource;

import java.util.List;

public interface IRoleResourceHelper {
    String addRoleResource(String roleId, String resourceId) throws Exception;
    void deleteByRole(String roleId) throws Exception;
    void deleteByRole(String roleId, List<String> resourceIdList) throws Exception;
    void deleteByResource(String resourceId) throws Exception;
    void deleteByResource(String resourceId, List<String> roleIdList) throws Exception;
}
