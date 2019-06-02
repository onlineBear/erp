package org.zmqy.erp.web.controller.mis.pc.component.sys.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zmqy.erp.model.mis.vo.Response;
import org.zmqy.erp.service.mis.pc.component.sys.security.ResourceCpmService;

import java.util.Map;

@RestController
@RequestMapping("/pc/component/resource")
public class ResourceCpmController {

    @Autowired
    private ResourceCpmService service;

    @PostMapping("/getMenuResource")
    private Response getMenuResource(@RequestBody Map<String, Object> paramsMap) {
        String userId = (String)paramsMap.get("loginUserId");
        String langId = (String)paramsMap.get("loginLangId");
        String menuNo = (String)paramsMap.get("menuNo");
        return Response.ok(this.service.getMenuResource(userId, langId, menuNo));
    }
}
