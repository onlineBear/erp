package org.anson.miniProject.controller.pc.account;

import lombok.extern.slf4j.Slf4j;
import org.anson.miniProject.constrant.dict.ClientEnum;
import org.anson.miniProject.service.account.model.LoginDTO;
import org.anson.miniProject.service.account.model.LogoutDTO;
import org.anson.miniProject.framework.pojo.CommonParam;
import org.anson.miniProject.framework.res.ResHelper;
import org.anson.miniProject.framework.res.Response;
import org.anson.miniProject.service.account.UserService;
import org.anson.miniProject.tool.helper.CommonParamHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/pc/account")
@Slf4j
public class AccountController {
    @Autowired
    private UserService service;
    @Autowired
    private HttpServletRequest req;

    private final ClientEnum clientKey = ClientEnum.PC;

    @PostMapping("/login")
    public Response pcLogin(@RequestBody @Validated LoginDTO dto) throws Exception{
        CommonParam commonParam = CommonParamHelper.buildCommonParam(req, clientKey, null);

        this.service.pcLogin(dto, commonParam);

        return ResHelper.ok(commonParam.getOperTime());
    }

    @PostMapping("/logout")
    public Response pcLogout(@RequestBody @Validated LogoutDTO dto) throws Exception{
        CommonParam commonParam = CommonParamHelper.buildCommonParam(req, clientKey, null);

        this.service.pcLogout(dto, commonParam);

        return ResHelper.ok(commonParam.getOperTime());
    }
}
