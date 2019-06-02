package org.zmqy.erp.web.controller.mis.pc.component.sys.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zmqy.erp.model.mis.vo.Response;
import org.zmqy.erp.service.mis.pc.component.sys.base.CommonCpmService;
import org.zmqy.erp.service.mis.pc.component.sys.base.MenuCpmService;

import java.util.Map;

@RestController
@RequestMapping("/pc/component/common")
public class CommonCpmController {

    @Autowired
    private CommonCpmService service;

    @PostMapping("/getCommon")
    private Response getCommon(@RequestBody Map<String, Object> paramsMap) {
        paramsMap.put("langId", paramsMap.get("loginLangId"));
        return Response.ok(this.service.getCommon(paramsMap));
    }
}
