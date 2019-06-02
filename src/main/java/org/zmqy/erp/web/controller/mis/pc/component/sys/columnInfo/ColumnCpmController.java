package org.zmqy.erp.web.controller.mis.pc.component.sys.columnInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zmqy.erp.model.mis.vo.Response;
import org.zmqy.erp.service.mis.pc.component.sys.columnInfo.ColumnCpmService;

import java.util.Map;

/**
 * @Description: 用户列配置
 * @Auther: lly
 * @Date: 2018-12-18 11:42
 */
@RestController
@RequestMapping("/pc/module/columnCpm")
public class ColumnCpmController {
    @Autowired
    private ColumnCpmService columnCpmService;

    @PostMapping("/getUseHeadColumnConfig")
    public Response getUseHeadColumnConfig(@RequestBody Map<String, Object> paramsMap) throws Exception {
        return Response.ok(columnCpmService.getUseHeadColumnConfig(paramsMap));
    }

    @PostMapping("/mdfUserHeadColumn")
    public Response mdfUserHeadColumn(@RequestBody Map<String, Object> paramsMap) throws Exception {
        return Response.ok(columnCpmService.mdfUserHeadColumn(paramsMap));
    }

    @PostMapping("/getUserHeadColumn")
    public Response getUserHeadColumn(@RequestBody Map<String, Object> paramsMap) throws Exception {
        return Response.ok(columnCpmService.getUserHeadColumn(paramsMap));
    }

    @PostMapping("/getUseDelColumnConfig")
    public Response getUseDelColumnConfig(@RequestBody Map<String, Object> paramsMap) throws Exception {
        return Response.ok(columnCpmService.getUseDelColumnConfig(paramsMap));
    }

    @PostMapping("/mdfUserDelColumn")
    public Response mdfUserDelColumn(@RequestBody Map<String, Object> paramsMap) throws Exception {
        columnCpmService.mdfUserDelColumn(paramsMap);
        return Response.ok();
    }


    @PostMapping("/addUserColumnWidth")
    public Response mdfUserColumnWidth(@RequestBody Map<String, Object> paramsMap) throws Exception {
        columnCpmService.mdfUserColumnWidth(paramsMap);
        return Response.ok();
    }

    @PostMapping("/getDefaultHeadColumn")
    public Response getDefaultHeadColumn(@RequestBody Map<String, Object> paramsMap) throws Exception {
        return Response.ok(columnCpmService.getDefaultHeadColumn(paramsMap));
    }

    @PostMapping("/getDefaultDtlColumn")
    public Response getDefaultDtlColumn(@RequestBody Map<String, Object> paramsMap) throws Exception {
        return Response.ok(columnCpmService.getDefaultDtlColumn(paramsMap));
    }

}
