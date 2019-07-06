package org.anson.miniProject.controller.pc.account;

import lombok.extern.slf4j.Slf4j;
import org.anson.miniProject.service.account.UserService;
import org.anson.miniProject.framework.res.ResHelper;
import org.anson.miniProject.framework.res.Response;
import org.anson.miniProject.core.model.dto.service.account.userService.LoginDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/pc/account")
@Slf4j
public class AccountController {
    @Autowired
    private UserService service;

    @PostMapping("/login")
    public Response pcLogin(@RequestBody @Validated LoginDto dto) throws Exception{
        Date operTime = new Date();
        String ipv4 = "ipv4Test";

        this.service.pcLogin(dto, ipv4, operTime);

        return ResHelper.ok(operTime);
    }
}
