package org.anson.miniProject.web.controller.pc.account;

import lombok.extern.slf4j.Slf4j;
import org.anson.miniProject.framework.res.ResHelper;
import org.anson.miniProject.framework.res.Response;
import org.anson.miniProject.framework.shiro.ShiroHelper;
import org.anson.miniProject.service.account.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.apache.shiro.subject.Subject;

import java.util.Map;

/**
 * @ClassName AccountController
 * @Description TODO
 * @Author wanganxiong
 * @Date 2019/6/19 10:06
 * @Version 1.0
 **/
@RestController
@RequestMapping("/pc/account")
@Slf4j
public class AccountController {
    @Autowired
    private UserService userService;
    /**
     * 登录
     * @return
     */
    @PostMapping("/login")
    public Response login(@RequestBody Map<String, Object> paramsMap) throws Exception{
        String no = (String) paramsMap.get("no");
        String psd = (String) paramsMap.get("psd");

        userService.login(no, psd);
        return ResHelper.ok();
    }
}
