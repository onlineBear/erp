package org.zmqy.erp.domain.mis.sys.security;

import org.zmqy.erp.domain.mis.IBaseDomain;
import org.zmqy.erp.model.mis.bo.sys.security.RoleBo;

import java.util.Date;
import java.util.List;

public interface IRoleDomain extends IBaseDomain<RoleBo> {
    /**
     * 修改角色权限
     * @param roleId
     * @param ownUserIdList
     * @param ownMenuResIdList
     * @param operUserId
     * @param operTime
     * @throws Exception
     */
    public void mdfRolePower(String roleId, List<String> ownUserIdList, List<String> ownMenuResIdList,List<String> ownProgramDraList,
                             String operUserId, Date operTime) throws Exception;
}
