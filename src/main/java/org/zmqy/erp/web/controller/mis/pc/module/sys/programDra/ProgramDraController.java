package org.zmqy.erp.web.controller.mis.pc.module.sys.programDra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.zmqy.erp.model.mis.vo.Response;
import org.zmqy.erp.service.mis.pc.module.sys.programDra.ProgramDraService;

import java.util.Map;

@RestController
@RequestMapping("/pc/module/pc010211/")
public class ProgramDraController {
    @Autowired
    ProgramDraService programDraService;


    @PostMapping("add")
    public Response add(@RequestBody Map<String, Object> paramsMap)throws Exception{
        this.programDraService.add(paramsMap);
        System.out.println(paramsMap);
        return Response.ok();
    }

    @PostMapping("getProgramDraList")
    public Response getProgramDraList(@RequestBody Map<String, Object> paramsMap)throws Exception{
        String langId = (String)paramsMap.get("loginLangId");
        return Response.ok(this.programDraService.getProgramDraList(langId));
    }

    @PostMapping("getProgramDraDtlList")
    public Response getProgramDraDtlList(@RequestBody Map<String, Object> paramsMap)throws Exception{
        String programDraId = (String)paramsMap.get("programDraId");
        String langId = (String)paramsMap.get("loginLangId");
        return Response.ok(this.programDraService.getProgramDraDtlList(programDraId, langId));
    }

    @PostMapping("mdfProgramDra")
    public Response mdfProgramDra(@RequestBody Map<String, Object> paramsMap)throws Exception{
        this.programDraService.mdfProgramDra(paramsMap);
        return Response.ok();
    }

    @PostMapping("delProgramDra")
    public Response delProgramDra(@RequestBody Map<String, Object>paramsMap)throws Exception{
        this.programDraService.delProgramDra(paramsMap);
        return Response.ok();
    }

    @PostMapping("mdfSeq")
    public Response mdfSeq(@RequestBody Map<String, Object>paramsMap)throws Exception{
        programDraService.mdfSeq(paramsMap);
        return Response.ok();
    }






}
