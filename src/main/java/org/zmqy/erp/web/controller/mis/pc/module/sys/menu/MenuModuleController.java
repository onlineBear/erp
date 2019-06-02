package org.zmqy.erp.web.controller.mis.pc.module.sys.menu;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zmqy.erp.model.mis.vo.Response;
import org.zmqy.erp.service.mis.pc.module.sys.menu.MenuModuleService;

import java.util.Map;

@RestController
@RequestMapping("/pc/module/pc010201")
public class MenuModuleController {

    @Autowired
    private MenuModuleService service;

    @PostMapping("/getMenuList")
    //@RequiresPermissions("/pc/module/pc010201/getMenuList")
    public Response getMenuList(@RequestBody Map<String, Object> paramsMap) {
        String langId = (String)paramsMap.get("loginLangId");
        return Response.ok(this.service.getMenuList(langId));
    }

    @PostMapping("/getResourceByMenu")
    public Response getResourceByMenu(@RequestBody Map<String, Object> paramsMap) {
        String langId = (String)paramsMap.get("loginLangId");
        String menuId = (String)paramsMap.get("menuId");
        return Response.ok(this.service.getResourceByMenu(langId, menuId));
    }

    @PostMapping("/addMenu")
    public Response addMenu(@RequestBody Map<String, Object> paramsMap) throws Exception {
        return Response.ok(this.service.addMenu(paramsMap));
    }

    @PostMapping("/mdfMenu")
    public Response mdfMenu(@RequestBody Map<String, Object> paramsMap) throws Exception{
        this.service.mdfMenu(paramsMap);
        return Response.ok();
    }

    @PostMapping("/mdfMenuSeq")
    public Response mdfMenuSeq(@RequestBody Map<String, Object> paramsMap) throws Exception{

        String menuId = (String)paramsMap.get("menuId");
        String parentMenuId = (String)paramsMap.get("parentMenuId");
        String frontMenuId = (String)paramsMap.get("frontMenuId");
        String behindMenuId = (String)paramsMap.get("behindMenuId");
        String operUserId = (String)paramsMap.get("loginUserId");

        this.service.mdfMenuSeq(menuId, parentMenuId, frontMenuId, behindMenuId, operUserId);
        return Response.ok();
    }
}
