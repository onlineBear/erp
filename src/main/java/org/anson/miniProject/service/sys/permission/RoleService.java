package org.anson.miniProject.service.sys.permission;

import org.anson.miniProject.core.biz.sys.permission.RoleBiz;
import org.anson.miniProject.core.model.bo.sys.permission.role.AddRoleBO;
import org.anson.miniProject.core.model.dto.service.sys.permission.role.AddRoleDTO;
import org.anson.miniProject.core.model.po.sys.permission.Role;
import org.anson.miniProject.core.model.vo.sys.permission.role.AddRoleVO;
import org.anson.miniProject.framework.log.service.PkClassFrom;
import org.anson.miniProject.framework.log.service.ServiceLog;
import org.anson.miniProject.framework.pojo.CommonParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class RoleService {
    @Autowired
    private RoleBiz biz;

    @ServiceLog(description = "新增角色", pkCalssFrom = PkClassFrom.RETURN, mainTableName = Role.__TABLENAME)
    public AddRoleVO addRole(AddRoleDTO dto, CommonParam cmParam) throws Exception{
        AddRoleBO bo = AddRoleDTO.toAddRoleBO(dto);
        String id = this.biz.addRole(bo, cmParam.getLoginUserId(), cmParam.getOperTime());
        return AddRoleVO.builder().id(id).build();
    }
}
