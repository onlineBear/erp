package org.anson.miniProject.controller.pc;

import org.anson.miniProject.core.model.dto.service.AddExampleDTO;
import org.anson.miniProject.framework.res.ResHelper;
import org.anson.miniProject.framework.res.Response;
import org.anson.miniProject.framework.shiro.ShiroHelper;
import org.anson.miniProject.service.ExampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/pc/example")
public class ExampleController {
    @Autowired
    private ExampleService service;

    @PostMapping("/add")
    public Response add(@RequestBody @Validated AddExampleDTO dto) throws Exception{
        Date operTime = new Date();

        this.service.add(dto, ShiroHelper.getUserId(), operTime);

        return ResHelper.ok(operTime);
    }
}
