package org.anson.miniProject.controller.pc.menu.sys;

import lombok.extern.slf4j.Slf4j;
import org.anson.miniProject.constrant.dict.ClientEnum;
import org.anson.miniProject.framework.log.service.UserOperLog;
import org.anson.miniProject.framework.pojo.CommonParam;
import org.anson.miniProject.framework.res.ResHelper;
import org.anson.miniProject.framework.res.Response;
import org.anson.miniProject.service.sys.dictType.DictTypeService;
import org.anson.miniProject.service.sys.dictType.model.AddDictTypeDTO;
import org.anson.miniProject.service.sys.dictType.model.UpdDictTypeDTO;
import org.anson.miniProject.tool.helper.CommonParamHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping("/pc/menu/sys/dictType")
@Slf4j
public class DictTypeController {
    @Autowired
    private DictTypeService service;

    private static final String menuId = "dictType";

    @PostMapping("/add")
    @UserOperLog(valKey = "no", description = "新增了数据字典类型",
                 clientKey = ClientEnum.PC, menuId = menuId)
    public Response addDictType(@RequestBody @Validated AddDictTypeDTO dto) throws Exception{
        return ResHelper.ok(this.service.addDictType(dto), new Date());
    }

    @PostMapping("/mdf")
    @UserOperLog(valKey = "id", description = "修改了数据字典类型",
                 clientKey = ClientEnum.PC, menuId = menuId)
    public Response mdfDictType(@RequestBody @Validated UpdDictTypeDTO dto) throws Exception{
        this.service.mdfDictType(dto);
        return ResHelper.ok(new Date());
    }
}

