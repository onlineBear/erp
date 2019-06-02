package org.zmqy.erp.domain.mis.sys.security;

import org.zmqy.erp.model.mis.bo.sys.security.RoleUserBo;

import java.util.Date;
import java.util.List;

public interface IRoleUserDomain {
    public String add(RoleUserBo bo, String operUserId, Date operTime) throws Exception;
    public void mdf(String roleId, List<String> allUserIdList, String operUserId, Date operTime) throws Exception;
    public String save(RoleUserBo bo, String operUserId, Date operTime) throws Exception;
}
