package org.anson.miniProject.service.sys.permission;

import lombok.extern.slf4j.Slf4j;
import org.anson.miniProject.core.biz.sys.permission.RoleBiz;
import org.anson.miniProject.core.model.bo.sys.permission.role.AddRoleBO;
import org.anson.miniProject.core.model.bo.sys.permission.role.MdfRoleBO;
import org.anson.miniProject.core.model.dto.service.sys.permission.role.AddRoleDTO;
import org.anson.miniProject.core.model.dto.service.sys.permission.role.DelRoleDTO;
import org.anson.miniProject.core.model.dto.service.sys.permission.role.MdfRoleDTO;
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
@Slf4j
public class RoleService {
    @Autowired
    private RoleBiz biz;

    @ServiceLog(description = "新增角色", pkCalssFrom = PkClassFrom.RETURN, mainTableName = Role.__TABLENAME)
    public AddRoleVO addRole(AddRoleDTO dto, CommonParam cmParam) throws Exception{
        AddRoleBO bo = AddRoleDTO.toAddRoleBO(dto);
        String id = this.biz.add(bo, cmParam.getLoginUserId(), cmParam.getOperTime());
        return AddRoleVO.builder().id(id).build();
    }

    @ServiceLog(description = "修改角色", valKey = "id",
                pkKey = "id", pkCalssFrom = PkClassFrom.INPUT,
                mainTableName = Role.__TABLENAME)
    public void mdfRole(MdfRoleDTO dto, CommonParam cmParam) throws Exception{
        MdfRoleBO bo = MdfRoleDTO.toMdfRoleBO(dto);
        this.biz.mdf(bo, cmParam.getLoginUserId(), cmParam.getOperTime());
    }

    @ServiceLog(description = "删除角色", valKey = "id", mainTableName = Role.__TABLENAME)
    public void delRole(DelRoleDTO dto, CommonParam cmParam) throws Exception{
        this.biz.del(dto.getId(), cmParam.getLoginUserId(), cmParam.getOperTime());
    }
}
