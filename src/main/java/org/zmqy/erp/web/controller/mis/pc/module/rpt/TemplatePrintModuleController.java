package org.zmqy.erp.web.controller.mis.pc.module.rpt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.zmqy.erp.model.mis.vo.Response;
import org.zmqy.erp.service.mis.pc.module.rpt.ReportDesignModuleService;
import org.zmqy.erp.service.mis.pc.module.rpt.TemplatePrintModuleService;

import java.util.List;
import java.util.Map;

/**
 * @Description: 打印模板管理
 * @Author: lly
 * @Date: 2019-02-13 12:26
 */
@RestController
@RequestMapping("/pc/module/TemplatePrintModule")
public class TemplatePrintModuleController {
    @Autowired
    private TemplatePrintModuleService service;


    /**
     * 查询某报表下的所有打印模板
     *
     * @param paramsMap
     * @return
     * @throws Exception
     */
    @PostMapping("/getReportTemplate")
    public Response getReportTemplate(@RequestBody Map<String, Object> paramsMap) throws Exception {
        return Response.ok(this.service.getReportTemplate(paramsMap));
    }

    /**
     * 修改模板配置
     *
     * @param paramsMap
     * @return
     * @throws Exception
     */
    @PostMapping("/mdfTemplateConfig")
    public Response mdfTemplateConfig(@RequestBody Map<String, Object> paramsMap) throws Exception {
        this.service.mdfTemplateConfig(paramsMap);
        return Response.ok();
    }

    /**
     * 上传打印模板
     *
     * @param
     * @return
     * @throws Exception
     */
    @PostMapping("/uploadTemplate")
    public Response uploadTemplate(@RequestParam(value = "file", required = false) MultipartFile file, String reportId, String loginUserId) throws Exception {
        this.service.uploadTemplate(file, reportId,loginUserId);
        return Response.ok();
    }

    /**
     * 模板打印,生成html文件
     *
     * @param
     * @return
     * @throws Exception
     */
    @PostMapping("/TemplatePrint")
    public Response TemplatePrint(@RequestBody Map<String, Object> paramsMap) throws Exception {
        return Response.ok(this.service.TemplatePrint(paramsMap));
    }

    /**
     * 刪除模板文件
     *
     * @param
     * @return
     * @throws Exception
     */
    @PostMapping("/delTemplate")
    public Response delTemplate(@RequestBody Map<String, Object> paramsMap) throws Exception {
        this.service.delTemplate(paramsMap);
        return Response.ok();
    }




}
