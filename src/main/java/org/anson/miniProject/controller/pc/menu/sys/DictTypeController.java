package org.anson.miniProject.controller.pc.menu.sys;

import lombok.extern.slf4j.Slf4j;
import org.anson.miniProject.constrant.dict.ClientEnum;
import org.anson.miniProject.controller.pc.BaseController;
import org.anson.miniProject.framework.log.service.ServiceLog;
import org.anson.miniProject.service.sys.dictType.model.AddDictTypeDTO;
import org.anson.miniProject.service.sys.dictType.model.UpdDictTypeDTO;
import org.anson.miniProject.framework.pojo.CommonParam;
import org.anson.miniProject.framework.res.ResHelper;
import org.anson.miniProject.framework.res.Response;
import org.anson.miniProject.service.sys.dictType.DictTypeService;
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
public class DictTypeController extends BaseController {
    @Autowired
    private DictTypeService service;
    @Autowired
    private HttpServletRequest req;

    @PostMapping("/add")
    @ServiceLog(valKey = "no", description = "新增了数据字典类型")
    public Response addDictType(@RequestBody @Validated AddDictTypeDTO dto) throws Exception{
        CommonParam commonParam = CommonParamHelper.buildCommonParam(req, clientKey, menuId);
        return ResHelper.ok(this.service.addDictType(dto, commonParam), commonParam.getOperTime());
    }

    @PostMapping("/mdf")
    public Response mdfDictType(@RequestBody @Validated UpdDictTypeDTO dto) throws Exception{
        CommonParam commonParam = CommonParamHelper.buildCommonParam(req, clientKey, menuId);
        this.service.mdfDictType(dto, commonParam);
        return ResHelper.ok(commonParam.getOperTime());
    }

    @Override
    protected void setClientKey() {
        super.clientKey = ClientEnum.PC;
    }

    @Override
    protected void setMenuId() {
        super.menuId = "dictType";
    }
}

