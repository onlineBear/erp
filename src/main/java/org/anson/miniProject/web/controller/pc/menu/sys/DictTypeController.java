package org.anson.miniProject.web.controller.pc.menu.sys;

import lombok.extern.slf4j.Slf4j;
import org.anson.miniProject.framework.res.ResHelper;
import org.anson.miniProject.framework.res.Response;
import org.anson.miniProject.framework.shiro.ShiroHelper;
import org.anson.miniProject.model.service.dictType.DictTypeAddDTO;
import org.anson.miniProject.model.service.dictType.DictTypeMdfDTO;
import org.anson.miniProject.service.sys.DictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/pc/menu/sys/dictType")
@Slf4j
public class DictTypeController {
    @Autowired
    private DictTypeService service;

    @PostMapping("/add")
    public Response addDictType(@RequestBody @Validated DictTypeAddDTO dto){
        Date nowTime = new Date();
        return ResHelper.ok(this.service.addDictType(dto, ShiroHelper.getUserId(), nowTime), nowTime);
    }

    @PostMapping("/mdf")
    public Response mdfDictType(@RequestBody @Validated DictTypeMdfDTO dto){
        Date nowTime = new Date();
        this.service.mdfDictType(dto, ShiroHelper.getUserId(), nowTime);
        return ResHelper.ok(nowTime);
    }

    @PostMapping("/del")
    public Response delDictType(@RequestBody String dictTypeId){
        Date nowTime = new Date();
        this.service.delDictType(dictTypeId, ShiroHelper.getUserId(), nowTime);
        return ResHelper.ok(nowTime);
    }
}

