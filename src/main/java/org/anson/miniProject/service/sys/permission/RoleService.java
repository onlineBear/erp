package org.anson.miniProject.service.sys.permission;

import lombok.extern.slf4j.Slf4j;
import org.anson.miniProject.core.model.dto.service.sys.permission.role.AddRoleDTO;
import org.anson.miniProject.core.model.dto.service.sys.permission.role.DelRoleDTO;
import org.anson.miniProject.core.model.dto.service.sys.permission.role.MdfRoleDTO;
import org.anson.miniProject.core.model.param.sys.permission.role.AddRoleParam;
import org.anson.miniProject.core.model.param.sys.permission.role.MdfRoleParam;
import org.anson.miniProject.core.model.po.sys.permission.Role;
import org.anson.miniProject.core.model.vo.sys.permission.role.AddRoleVO;
import org.anson.miniProject.domain.sys.permission.role.IRoleDMService;
import org.anson.miniProject.framework.log.service.PkClassFrom;
import org.anson.miniProject.framework.log.service.ServiceLog;
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

    private static final String MAINTABLENAME = Role.__TABLENAME;

    @ServiceLog(description = "新增角色", valKey = "no",
            pkCalssFrom = PkClassFrom.RETURN, pkKey = "id",
            mainTableName = MAINTABLENAME)
    public AddRoleVO addRole(AddRoleDTO dto, CommonParam cmParam) throws Exception{
        AddRoleParam param = AddRoleDTO.toAddRoleBO(dto);
        //String id = this.domain.add(param, cmParam.getLoginUserId(), cmParam.getOperTime());
        return AddRoleVO.builder().id("").build();
    }

    @ServiceLog(description = "修改角色", valKey = "id",
                pkKey = "id", pkCalssFrom = PkClassFrom.INPUT,
                mainTableName = Role.__TABLENAME)
    public void mdfRole(MdfRoleDTO dto, CommonParam cmParam) throws Exception{
        MdfRoleParam param = MdfRoleDTO.toMdfRoleParam(dto);
       // this.domain.mdf(param, cmParam.getLoginUserId(), cmParam.getOperTime());
    }

    public void delRole(DelRoleDTO dto, CommonParam cmParam) throws Exception{
        //this.domain.del(dto.getId(), cmParam.getLoginUserId(), cmParam.getOperTime());
    }
}
