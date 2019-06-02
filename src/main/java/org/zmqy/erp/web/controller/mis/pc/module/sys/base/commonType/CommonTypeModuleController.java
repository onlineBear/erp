package org.zmqy.erp.web.controller.mis.pc.module.sys.base.commonType;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zmqy.erp.model.mis.vo.Response;
import org.zmqy.erp.service.mis.pc.module.sys.base.CommonTypeModuleService;

import java.util.Map;

@RestController
@RequestMapping("/pc/module/pc010208")
public class CommonTypeModuleController {
    @Autowired
    private CommonTypeModuleService service;

    @PostMapping("/getList")
    @RequiresPermissions("/pc/module/pc010208/sel")
    public Response getList(@RequestBody Map paramsMap){
        return Response.ok(this.service.getList(paramsMap));
    }

    @PostMapping("/getOne")
    @RequiresPermissions("/pc/module/pc010208/sel")
    public Response getOne(@RequestBody Map paramsMap){
        return Response.ok(this.service.getOne(paramsMap));
    }

    @PostMapping("/add")
    @RequiresPermissions("/pc/module/pc010208/add")
    public Response add(@RequestBody Map paramsMap) throws Exception{
        return Response.ok(this.service.add(paramsMap));
    }

    @PostMapping("/mdf")
    @RequiresPermissions("/pc/module/pc010208/mdf")
    public Response mdf(@RequestBody Map paramsMap) throws Exception{
        return Response.ok(this.service.mdf(paramsMap));
    }
}
