package org.anson.miniProject.controller.pc.menu.sys;

import lombok.extern.slf4j.Slf4j;
import org.anson.miniProject.constrant.dict.ClientEnum;
import org.anson.miniProject.core.model.service.dictType.AddDictTypeDTO;
import org.anson.miniProject.core.model.service.dictType.DictTypeDelDTO;
import org.anson.miniProject.core.model.service.dictType.DictTypeMdfDTO;
import org.anson.miniProject.framework.pojo.CommonParam;
import org.anson.miniProject.framework.res.ResHelper;
import org.anson.miniProject.framework.res.Response;
import org.anson.miniProject.service.sys.DictTypeService;
import org.anson.miniProject.tool.helper.CommonParamHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/pc/menu/sys/dictType")
@Slf4j
public class DictTypeController {
    @Autowired
    private DictTypeService service;
    @Autowired
    private HttpServletRequest req;

    private final ClientEnum clientKey = ClientEnum.PC;
    private final String menuId = "dictType";

    @PostMapping("/add")
    public Response addDictType(@RequestBody @Validated AddDictTypeDTO dto) throws Exception{
        CommonParam commonParam = CommonParamHelper.buildCommonParam(req, clientKey, menuId);
        return ResHelper.ok(this.service.addDictType(dto, commonParam), commonParam.getOperTime());
    }

    @PostMapping("/mdf")
    public Response mdfDictType(@RequestBody @Validated DictTypeMdfDTO dto) throws Exception{
        CommonParam commonParam = CommonParamHelper.buildCommonParam(req, clientKey, menuId);
        this.service.mdfDictType(dto, commonParam);
        return ResHelper.ok(commonParam.getOperTime());
    }

    @PostMapping("/del")
    public Response delDictType(@RequestBody @Validated DictTypeDelDTO dto) throws Exception {
        CommonParam commonParam = CommonParamHelper.buildCommonParam(req, clientKey, menuId);
        this.service.delDictType(dto.getId(), commonParam);
        return ResHelper.ok(commonParam.getOperTime());
    }
}

