package org.zmqy.erp.web.controller.mis.pc.module.sys.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zmqy.erp.model.mis.vo.Response;
import org.zmqy.erp.service.mis.pc.module.sys.common.CommonModuleService;

import java.util.Map;

@RestController
@RequestMapping("/pc/module/tmpModule")
public class CommonModuleController {

    @Autowired
    private CommonModuleService service;

    @PostMapping("/add")
    public Response add(@RequestBody Map<String, Object> paramsMap) throws Exception{
        this.service.add(paramsMap);
        return Response.ok();
    }
}
