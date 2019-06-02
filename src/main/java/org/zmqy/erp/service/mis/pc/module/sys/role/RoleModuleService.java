package org.zmqy.erp.service.mis.pc.module.sys.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zmqy.erp.domain.mis.sys.base.IMenuDomain;
import org.zmqy.erp.domain.mis.sys.security.*;
import org.zmqy.erp.mapper.mis.pc.module.sys.role.RoleModuleMapper;
import org.zmqy.erp.model.mis.bo.sys.security.RoleBo;
import org.zmqy.erp.model.mis.bo.sys.security.RoleMenuBo;
import org.zmqy.erp.model.mis.bo.sys.security.RoleResourceBo;
import org.zmqy.erp.model.mis.bo.sys.security.RoleUserBo;

import java.util.*;

@Service
public class RoleModuleService {
    @Autowired
    private RoleModuleMapper mapper;
    @Autowired
    private IRoleDomain domain;

    public List<Map> getRoleList(String langId){
        return this.mapper.getRoleList(langId);
    }

    public Map getUserAndResource(String langId, String roleId){
        Map rsMap = new HashMap();
        rsMap.put("userList", this.mapper.getUserByRole(roleId));
        rsMap.put("resList", this.mapper.getResByRole(langId, roleId));
        rsMap.put("programDraList", this.mapper.getProgramDraByRole(langId, roleId));

        return rsMap;
    }

    @Transactional(rollbackFor = Exception.class)
    public void mdfRolePower(Map<String, Object> paramsMap) throws Exception{
        Date operTime = new Date();

        String operUserId = (String)paramsMap.get("loginUserId");

        String roleId = (String)paramsMap.get("roleId");
        List<String> ownUserList = (List<String>)paramsMap.get("ownUserList");
        List<String> ownMenuResList = (List<String>)paramsMap.get("ownMenuResList");
        List<String> ownProgramDraList = (List<String>)paramsMap.get("ownProgramDraList");

        this.domain.mdfRolePower(roleId, ownUserList, ownMenuResList,ownProgramDraList,operUserId, operTime);
    }
}
