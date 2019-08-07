package org.anson.miniProject.service.sys.permission;

import lombok.extern.slf4j.Slf4j;
import org.anson.miniProject.core.model.dto.service.sys.permission.role.AddRoleDTO;
import org.anson.miniProject.core.model.dto.service.sys.permission.role.DelRoleDTO;
import org.anson.miniProject.core.model.dto.service.sys.permission.role.MdfRoleDTO;
import org.anson.miniProject.core.model.vo.sys.permission.role.AddRoleVO;
import org.anson.miniProject.domain.sys.permission.role.IRoleDMService;
import org.anson.miniProject.framework.pojo.CommonParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class RoleService {
    @Autowired
    private IRoleDMService roleDMService;

    private static final String MAINTABLENAME = "2";

    public AddRoleVO addRole(AddRoleDTO dto, CommonParam cmParam) throws Exception{
        //String id = this.domain.add(param, cmParam.getLoginUserId(), cmParam.getOperTime());
        return AddRoleVO.builder().id("").build();
    }

    public void mdfRole(MdfRoleDTO dto, CommonParam cmParam) throws Exception{

       // this.domain.mdf(param, cmParam.getLoginUserId(), cmParam.getOperTime());
    }

    public void delRole(DelRoleDTO dto, CommonParam cmParam) throws Exception{
        //this.domain.del(dto.getId(), cmParam.getLoginUserId(), cmParam.getOperTime());
    }
}
