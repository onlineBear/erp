package org.zmqy.erp.web.controller.mis.pc.module.sys.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zmqy.erp.model.mis.vo.Response;
import org.zmqy.erp.service.mis.pc.module.sys.role.RoleModuleService;

import java.util.Map;

@RestController
@RequestMapping("/pc/module/pc010202")
public class RoleModuleController {
    @Autowired
    private RoleModuleService service;

    @PostMapping("/getRoleList")
    public Response getRoleList(@RequestBody Map<String, Object> paramsMap){
        String langId = (String)paramsMap.get("loginLangId");
        return Response.ok(this.service.getRoleList(langId));
    }

    @PostMapping("/getUserAndResource")
    public Response getUserAndResource(@RequestBody Map<String, Object> paramsMap){
        String langId = (String)paramsMap.get("loginLangId");
        String roleId = (String)paramsMap.get("roleId");
        return Response.ok(this.service.getUserAndResource(langId, roleId));
    }

    @PostMapping("/mdfRolePower")
    public Response mdfRolePower(@RequestBody Map<String, Object> paramsMap) throws Exception{
        this.service.mdfRolePower(paramsMap);
        return Response.ok();
    }
}
