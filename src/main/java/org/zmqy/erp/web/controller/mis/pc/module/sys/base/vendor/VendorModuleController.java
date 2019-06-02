package org.zmqy.erp.web.controller.mis.pc.module.sys.base.vendor;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.zmqy.erp.model.mis.vo.Response;
import org.zmqy.erp.service.mis.pc.module.base.vendor.VendorModuleService;
import org.zmqy.erp.tool.helper.zmqy.security.Permition;

import java.util.Map;

@RestController
@RequestMapping("/pc/module/pc010501")
public class VendorModuleController {
    @Autowired
    private VendorModuleService service;

    @PostMapping("/getVendorList")
    @RequiresPermissions("/pc/module/pc010501/sel")
    public Response getVendorList(@RequestBody Map paramsMap){
        return Response.ok(this.service.getVendorList(paramsMap));
    }

    @PostMapping("/getVendorById")
    @RequiresPermissions("/pc/module/pc010501/sel")
    public Response getVendorById(@RequestBody Map paramsMap){
        return Response.ok(this.service.getVendorById(paramsMap));
    }

    @PostMapping("/add")
    @RequiresPermissions("/pc/module/pc010501/add")
    public Response add(@RequestBody Map paramsMap) throws Exception{
        return Response.ok(this.service.add(paramsMap));
    }

    @PostMapping("/mdf")
    @RequiresPermissions("/pc/module/pc010501/mdf")
    public Response mdf(@RequestBody Map paramsMap) throws Exception{
        return Response.ok(this.service.mdf(paramsMap));
    }

    @PostMapping("/export")
    @RequiresPermissions("/pc/module/pc010501/export")
    public Response export(@RequestBody Map paramsMap){
        return Response.ok(this.service.export(paramsMap));
    }

    @PostMapping("/ck/{nextLevel}")
    public Response check(@RequestBody Map paramsMap, @PathVariable("nextLevel")Integer nextLevel) throws Exception{
        // 检查权限
        Permition.checkReviewPermition("pc010501", nextLevel);

        paramsMap.put("nextLevel", nextLevel);
        return Response.ok(this.service.check(paramsMap));
    }

    @PostMapping("/bhCk/{nextLevel}")
    public Response bhCheck(@RequestBody Map paramsMap, @PathVariable("nextLevel")Integer nextLevel) throws Exception{
        // 检查权限
        Permition.checkReviewPermition("pc010501", nextLevel);

        paramsMap.put("nextLevel", nextLevel);
        return Response.ok(this.service.bhCheck(paramsMap));
    }

    @PostMapping("/unCk/{currentLevel}")
    public Response back(@RequestBody Map paramsMap, @PathVariable("currentLevel")Integer currentLevel) throws Exception{
        // 检查权限
        Permition.checkBackPermition("pc010501", currentLevel);

        paramsMap.put("currentLevel", currentLevel);
        return Response.ok(this.service.back(paramsMap));
    }
}
