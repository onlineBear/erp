package org.zmqy.erp.domain.mis.sys.security;

import org.zmqy.erp.model.mis.bo.sys.security.RoleResourceBo;

import java.util.Date;
import java.util.List;

public interface IRoleResourceDomain {
    public void mdf(String roleId, List<String> allResIdList, String operUserId, Date operTime) throws Exception;
    public void mdfProgramDra(String roleId, List<String> ownProgramDraList, String operUserId, Date operTime) throws Exception;

    public String add(RoleResourceBo bo, String operUserId, Date operTime) throws Exception;

    public String save(RoleResourceBo bo, String operUserId, Date operTime) throws Exception;
    //public void delByRole(String roleId, String operUserId, Date operTime) throws Exception;
}
