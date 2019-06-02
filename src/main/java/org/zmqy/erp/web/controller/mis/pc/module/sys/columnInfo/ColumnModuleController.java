package org.zmqy.erp.web.controller.mis.pc.module.sys.columnInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zmqy.erp.model.mis.vo.Response;
import org.zmqy.erp.service.mis.pc.module.sys.columnInfo.ColumnModuleService;

import java.util.Map;


/**
 * @Description: 系统列配置
 * @Auther: lly
 * @Date: 2018-12-17 09:19
 */

@RestController
@RequestMapping("/pc/module/columnModule")
public class ColumnModuleController {
    @Autowired
    private ColumnModuleService columnModuleService;

    @PostMapping("/addColumn")
    public Response addHeadColumnAndDtl(@RequestBody Map<String, Object> paramsMap) throws Exception {
        columnModuleService.addHeadColumnAndDtl(paramsMap);
        return Response.ok();
    }

    @PostMapping("/getColumnAll")
    public Response getColumnAll(@RequestBody Map<String, Object> paramsMap) throws Exception {
        return Response.ok(columnModuleService.getColumnAll(paramsMap));
    }

    @PostMapping("/getColumnDetail")
    public Response getColumnDetail(@RequestBody Map<String, Object> paramsMap) throws Exception {
        return Response.ok(columnModuleService.getColumnDetail(paramsMap));
    }
    @PostMapping("/getItColumnDetail")
    public Response getItColumnDetail(@RequestBody Map<String, Object> paramsMap) throws Exception {
        return Response.ok(columnModuleService.getItColumnDetail(paramsMap));
    }

    @PostMapping("/mdfColumnDetail")
    public Response mdfColumnDetail(@RequestBody Map<String, Object> paramsMap) throws Exception {
        columnModuleService.mdfColumnDetail(paramsMap);
        return Response.ok();
    }

    @PostMapping("/delColumnDetail")
    public Response delColumnDetail(@RequestBody Map<String, Object> paramsMap) throws Exception {
        columnModuleService.delColumnDetail(paramsMap);
        return Response.ok();
    }


    @PostMapping("/addFormula")
    public Response addFormula(@RequestBody Map<String, Object> paramsMap) throws Exception {
        columnModuleService.addFormula(paramsMap);
        return Response.ok();
    }

    @PostMapping("/getColumnNo")
    public Response getColumnNo(@RequestBody Map<String, Object> paramsMap) throws Exception {

        return Response.ok(columnModuleService.getColumnNoAll(paramsMap));
    }

    @PostMapping("/mdfColumnByIt")
    public Response mdfColumnByIt(@RequestBody Map<String, Object> paramsMap) throws Exception {
        columnModuleService.mdfColumnByIt(paramsMap);
        return Response.ok();
    }

    @PostMapping("/mdfFormula")
    public Response mdfFormula(@RequestBody Map<String, Object> paramsMap) throws Exception {
        columnModuleService.mdfFormula(paramsMap);
        return Response.ok();
    }

    @PostMapping("/delFormula")
    public Response delFormula(@RequestBody Map<String, Object> paramsMap) throws Exception {
        columnModuleService.delFormula(paramsMap);
        return Response.ok();
    }

}
