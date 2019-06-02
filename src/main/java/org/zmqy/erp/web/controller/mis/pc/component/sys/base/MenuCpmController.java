package org.zmqy.erp.web.controller.mis.pc.component.sys.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zmqy.erp.model.mis.vo.Response;
import org.zmqy.erp.service.mis.pc.component.sys.base.MenuCpmService;

import java.util.Map;

@RestController
@RequestMapping("/pc/component/menu")
public class MenuCpmController {

    @Autowired
    private MenuCpmService service;

    @PostMapping("/getUserMenu")
    private Response getUserMenu(@RequestBody Map<String, Object> paramsMap) {
        String userId = (String) paramsMap.get("loginUserId");
        String langId = (String) paramsMap.get("loginLangId");
        String storeId = (String) paramsMap.get("loginStoreId");

        return Response.ok(this.service.getUserMenu(userId, langId, storeId));
    }

    @PostMapping("/getMenu")
    private Response getMenu(@RequestBody Map<String, Object> paramsMap) {

        return Response.ok(this.service.getMenu(paramsMap));
    }
}
