package org.anson.miniProject.web.controller.pc.menu.sys;

import lombok.extern.slf4j.Slf4j;
import org.anson.miniProject.framework.res.ResHelper;
import org.anson.miniProject.framework.res.Response;
import org.anson.miniProject.framework.shiro.ShiroHelper;
import org.anson.miniProject.model.service.menu.MenuAddDTO;
import org.anson.miniProject.model.service.menu.MenuDelDTO;
import org.anson.miniProject.model.service.menu.MenuMdfDTO;
import org.anson.miniProject.biz.sys.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/pc/menu/sys/menu")
@Slf4j
public class MenuController {
    @Autowired
    private MenuService service;

    @PostMapping("/add")
    public Response addMenu(@RequestBody @Validated MenuAddDTO dto){
        Date nowTime = new Date();
        return ResHelper.ok(this.service.addMenu(dto, ShiroHelper.getUserId(), nowTime), nowTime);
    }

    @PostMapping("/mdf")
    public Response mdfMenu(@RequestBody @Validated MenuMdfDTO dto){
        Date nowTime = new Date();
        this.service.mdfMenu(dto, ShiroHelper.getUserId(), nowTime);
        return ResHelper.ok(nowTime);
    }

    @PostMapping("/get")
    public Response getMenu(){
        Date nowTime = new Date();
        return ResHelper.ok(this.service.getMenuByClient("pc"), nowTime);
    }

    @PostMapping("/del")
    public Response delMenu(@RequestBody MenuDelDTO dto){
        Date nowTime = new Date();
        this.service.delMenu(dto);
        return ResHelper.ok(nowTime);
    }
}

