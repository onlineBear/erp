package org.anson.miniProject.core.domain.sys.permission;

import java.util.Date;
import java.util.List;

public interface IRoleResourceDomain {
    String add(String roleId, String resourceId, String operUserId, Date operTime) throws Exception;
    void addByRole(String roleId, List<String> resourceIdList, String operUserId, Date operTime) throws Exception;
    void addByResource(String resourceId, List<String> roleIdList, String operUserId, Date operTime) throws Exception;
    //void del(String id, String operUserId, Date operTime) throws Exception;
    void delByResource(String resId, String operUserId, Date operTime) throws Exception;
    void delByRole(String roleId, String operUserId, Date operTime) throws Exception;
}
