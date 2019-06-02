package org.zmqy.erp.domain.mis.sys.security;

import org.zmqy.erp.model.mis.bo.sys.security.RoleMenuBo;

import java.util.Date;
import java.util.List;

public interface IRoleMenuDomain {
    public void mdf(String roleId, List<String> allMenuIdList, String operUserId, Date operTime) throws Exception;

    public String add(RoleMenuBo bo, String operUserId, Date operTime) throws Exception;


    public String save(RoleMenuBo bo, String operUserId, Date operTime) throws Exception;
    //public void delByRole(String roleId, String operUserId, Date operTime) throws Exception;

}
