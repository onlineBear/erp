package org.anson.miniProject.web.controller.pc.sys;

import lombok.extern.slf4j.Slf4j;
import org.anson.miniProject.framework.res.ResHelper;
import org.anson.miniProject.framework.res.Response;
import org.anson.miniProject.framework.shiro.ShiroHelper;
import org.anson.miniProject.model.dto.sys.menu.MenuAddDTO;
import org.anson.miniProject.service.sys.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/pc/menu")
@Slf4j
public class MenuController {
    @Autowired
    private MenuService service;

    @PostMapping("/add")
    public Response addMenu(@RequestBody @Validated MenuAddDTO dto){
        Date nowTime = new Date();
        this.service.addMenu(dto, ShiroHelper.getUserId(), nowTime);
        return ResHelper.ok();
    }
}
