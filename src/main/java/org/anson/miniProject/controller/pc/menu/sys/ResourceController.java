package org.anson.miniProject.controller.pc.menu.sys;

import lombok.extern.slf4j.Slf4j;
import org.anson.miniProject.constrant.dict.ClientEnum;
import org.anson.miniProject.core.model.dto.service.sys.permission.resource.AddResourceDTO;
import org.anson.miniProject.core.model.dto.service.sys.permission.resource.DelResourceDTO;
import org.anson.miniProject.framework.pojo.CommonParam;
import org.anson.miniProject.framework.res.ResHelper;
import org.anson.miniProject.framework.res.Response;
import org.anson.miniProject.service.sys.permission.ResourceService;
import org.anson.miniProject.tool.helper.CommonParamHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/pc/menu/sys/resource")
@Slf4j
public class ResourceController {
    @Autowired
    private ResourceService service;
    @Autowired
    private HttpServletRequest req;

    private final ClientEnum clientKey = ClientEnum.PC;
    private final String menuId = "resource";

    @PostMapping("/add")
    public Response addResource(@RequestBody @Validated AddResourceDTO dto) throws Exception{
        CommonParam commonParam = CommonParamHelper.buildCommonParam(req, clientKey, menuId);
        return ResHelper.ok(this.service.add(dto, commonParam), commonParam.getOperTime());
    }

    @PostMapping("/del")
    public Response delResource(@RequestBody @Validated DelResourceDTO dto) throws Exception{
        CommonParam commonParam = CommonParamHelper.buildCommonParam(req, clientKey, menuId);
        this.service.del(dto, commonParam);
        return ResHelper.ok(commonParam.getOperTime());
    }
}

