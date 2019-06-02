package org.zmqy.erp.web.controller.mis.pc.module.sys.flow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zmqy.erp.model.mis.vo.Response;
import org.zmqy.erp.service.mis.pc.module.sys.flow.FlowService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *自定义流程
 */
@RestController
@RequestMapping("/pc/module/pc010207")
public class FlowController {

    @Autowired
    private FlowService service;


    /**
     * 新增流程
     * @param paramsMap
     * @return
     * @throws Exception
     */
    @PostMapping("/add")
    public Response addFlow(@RequestBody Map<String, Object> paramsMap)throws Exception{
        this.service.add(paramsMap);
        return Response.ok();
    }


    /**
     * 查询流程
     * @param paramsMap
     * @return
     * @throws Exception
     */
    @PostMapping("/getFlowList")
    public Response getFlowList(@RequestBody Map<String, Object> paramsMap)throws Exception{
        String langId = (String)paramsMap.get("loginLangId");
        return Response.ok(this.service.getFlowList(langId));
    }


    /**
     * 查询层级信息
     * @param paramsMap
     * @return
     * @throws Exception
     */
    @PostMapping("/getFlowDtlList")
    public Response getFlowDtlList(@RequestBody Map<String,Object> paramsMap)throws Exception{
        String flowId = (String)paramsMap.get("flowId");
        String langId = (String)paramsMap.get("loginLangId");
        return Response.ok(this.service.getFlowDtlList(flowId, langId));
    }


    /**
     * 修改流程
     * @param paramsMap
     * @return
     * @throws Exception
     */
    @PostMapping("/mdfFlow")
    public Response mdfFlow(@RequestBody Map<String, Object> paramsMap)throws Exception{
        this.service.mdfFlow(paramsMap);
        return Response.ok();
    }


    /**
     * 删除流程
     * @param paramsMap
     * @return
     * @throws Exception
     */
    @PostMapping("/delFlow")
    public Response delFlow(@RequestBody Map<String, Object> paramsMap)throws Exception{
        this.service.delFlow(paramsMap);
        return Response.ok();
    }
/*

    /**
    *
    *查找单据详情
     **/
/*
    @PostMapping("/getCheckBill")
    public Response getCheckBill(@RequestBody Map<String, Object> paramsMap)throws Exception{
        String billId = (String)paramsMap.get("billId");
        String langId = (String)paramsMap.get("loginLangId");
        return Response.ok(this.service.getCheckBill(billId,langId));
    }
*/
}
