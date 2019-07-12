package org.anson.miniProject.controller.pc.menu.sys;

import lombok.extern.slf4j.Slf4j;
import org.anson.miniProject.constrant.dict.ClientEnum;
import org.anson.miniProject.core.model.dto.service.sys.permission.role.AddRoleDTO;
import org.anson.miniProject.core.model.dto.service.sys.permission.role.DelRoleDTO;
import org.anson.miniProject.core.model.dto.service.sys.permission.role.MdfRoleDTO;
import org.anson.miniProject.framework.pojo.CommonParam;
import org.anson.miniProject.framework.res.ResHelper;
import org.anson.miniProject.framework.res.Response;
import org.anson.miniProject.service.sys.permission.RoleService;
import org.anson.miniProject.tool.helper.CommonParamHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/pc/menu/sys/role")
@Slf4j
public class RoleController {
    @Autowired
    private RoleService service;
    @Autowired
    private HttpServletRequest req;

    private final ClientEnum clientKey = ClientEnum.PC;
    private final String menuId = "role";

    @PostMapping("/add")
    public Response addRole(@RequestBody @Validated AddRoleDTO dto) throws Exception{
        CommonParam commonParam = CommonParamHelper.buildCommonParam(req, clientKey, menuId);
        return ResHelper.ok(this.service.addRole(dto, commonParam), commonParam.getOperTime());
    }

    @PostMapping("/mdf")
    public Response mdfRole(@RequestBody @Validated MdfRoleDTO dto) throws Exception{
        CommonParam commonParam = CommonParamHelper.buildCommonParam(req, clientKey, menuId);
        this.service.mdfRole(dto, commonParam);
        return ResHelper.ok(commonParam.getOperTime());
    }

    @PostMapping("/del")
    public Response delRole(@RequestBody @Validated DelRoleDTO dto) throws Exception{
        CommonParam commonParam = CommonParamHelper.buildCommonParam(req, clientKey, menuId);
        this.service.delRole(dto, commonParam);
        return ResHelper.ok(commonParam.getOperTime());
    }
}

