package org.anson.miniProject.web.controller.pc.unauthorized;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.anson.miniProject.model.vo.Response;
import java.util.Map;

/**
 * 登录前的访问
 * 无需授权登录即可访问
 */
@RestController
@RequestMapping("/pc/unAuthorized")
public class UnAuthorizedController {

    @PostMapping(value = "/login")
    public Response login(@RequestBody Map<String, Object> paramsMap) throws Exception {
        String userNo = (String) paramsMap.get("userNo");
        String password = (String) paramsMap.get("password");
        String storeId = (String) paramsMap.get("storeId");
        String langId = (String) paramsMap.get("langId");
        return Response.ok();
    }
}
