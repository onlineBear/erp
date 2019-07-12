package org.anson.miniProject.core.domain.sys.permission;

import java.util.Date;
import java.util.List;

public interface IUserRoleDomain {
    void addByRole(String roleId, List<String> userIdList, String operUserId, Date operTime) throws Exception;
    void addByUser(String userId, List<String> roleIdList, String operUserId, Date operTime) throws Exception;
    void delByUser(String userId, String operUserId, Date operTime) throws Exception;
    void delByUser(String userId, List<String> roleIdList, String operUserId, Date operTime) throws Exception;
    void delByRole(String roleId, String operUserId, Date operTime) throws Exception;
    void delByRole(String roleId, List<String> userIdList, String operUserId, Date operTime) throws Exception;

    List<String> getUserIdListByRole(String roleId);
}
