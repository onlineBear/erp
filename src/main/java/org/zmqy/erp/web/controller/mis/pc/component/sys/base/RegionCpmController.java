package org.zmqy.erp.web.controller.mis.pc.component.sys.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.zmqy.erp.model.mis.vo.Response;
import org.zmqy.erp.service.mis.pc.component.sys.base.CommonCpmService;
import org.zmqy.erp.service.mis.pc.component.sys.base.RegionCpmService;

import java.util.Map;

@RestController
@RequestMapping("/pc/component/region")
public class RegionCpmController {

    @Autowired
    private RegionCpmService service;

    @GetMapping("/getList")
    private Response getList(@RequestParam("loginLangId") String langId) {
        return Response.ok(this.service.getList(langId));
    }
}
