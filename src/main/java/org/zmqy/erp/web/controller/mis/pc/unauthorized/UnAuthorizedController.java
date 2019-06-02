package org.zmqy.erp.web.controller.mis.pc.unauthorized;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zmqy.erp.model.mis.vo.Response;
import org.zmqy.erp.service.mis.pc.unauthorized.UnAuthorizedService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 登录前的访问
 * 无需授权登录即可访问
 */
@RestController
@RequestMapping("/pc/unAuthorized")
public class UnAuthorizedController {

    @Autowired
    private UnAuthorizedService service;

    /**
     * 查询语言
     *
     * @return
     */
    @PostMapping("/getLanguageList")
    public Response getLanguageList() {
        return Response.ok(this.service.getLanguageList());
    }

    @PostMapping("/getStoreList")
    public Response getStoreList(@RequestBody Map<String, Object> paramsMap) {
        String langNo = (String) paramsMap.get("langNo");
        String userNo = (String) paramsMap.get("userNo");
        return Response.ok(this.service.getStoreList(langNo, userNo));
    }

    @PostMapping(value = "/login")
    public Response login(@RequestBody Map<String, Object> paramsMap) throws Exception {
        String userNo = (String) paramsMap.get("userNo");
        String password = (String) paramsMap.get("password");
        String storeId = (String) paramsMap.get("storeId");
        String langId = (String) paramsMap.get("langId");
        return Response.ok(this.service.login(userNo, password, langId, storeId));
    }
}
