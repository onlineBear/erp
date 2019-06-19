package org.anson.miniProject.web.controller.pc.account;

import org.anson.miniProject.framework.res.ResHelper;
import org.anson.miniProject.framework.res.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName AccountController
 * @Description TODO
 * @Author wanganxiong
 * @Date 2019/6/19 10:06
 * @Version 1.0
 **/
@RestController
@RequestMapping("/account")
public class AccountController {

    /**
     * 登录
     * @return
     */
    @PostMapping("/login")
    public Response login(){
        return ResHelper.ok();
    }
}
