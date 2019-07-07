package org.anson.miniProject.controller.pc.account;

import lombok.extern.slf4j.Slf4j;
import org.anson.miniProject.service.account.UserService;
import org.anson.miniProject.framework.res.ResHelper;
import org.anson.miniProject.framework.res.Response;
import org.anson.miniProject.core.model.dto.service.account.userService.LoginDto;
import org.anson.miniProject.tool.helper.IpHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping("/pc/account")
@Slf4j
public class AccountController {
    @Autowired
    private UserService service;

    @PostMapping("/login")
    public Response pcLogin(@RequestBody @Validated LoginDto dto, HttpServletRequest req) throws Exception{
        Date operTime = new Date();
        String ipv4 = IpHelper.getRemoteHost(req);

        this.service.pcLogin(dto, ipv4, operTime);

        return ResHelper.ok(operTime);
    }
}
