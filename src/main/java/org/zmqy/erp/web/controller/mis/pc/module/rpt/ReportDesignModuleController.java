package org.zmqy.erp.web.controller.mis.pc.module.rpt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zmqy.erp.mapper.mis.biz.rpt.ReportDesignMapper;
import org.zmqy.erp.model.mis.vo.Response;
import org.zmqy.erp.service.mis.pc.module.rpt.ReportDesignModuleService;

import java.util.Map;

/**
 * @Description: 报表设计
 * @Author: lly
 * @Date: 2019-02-13 12:26
 */
@RestController
@RequestMapping("/pc/module/reportDesignModule")
public class ReportDesignModuleController {
    @Autowired
    private ReportDesignModuleService service;
    @Autowired
    private ReportDesignMapper reportDesignMapper;


    /**
     * 解析sql
     *
     * @param paramsMap
     * @return
     * @throws Exception
     */
    @PostMapping("/decodeSql")
    public Response decodeSql(@RequestBody Map<String, Object> paramsMap) throws Exception {
        return Response.ok(this.service.decodeSql(paramsMap));
    }

    /**
     * 获取sql中的固定查询参数
     *
     * @param paramsMap
     * @return
     * @throws Exception
     */
    @PostMapping("/getSqlParams")
    public Response getSqlParams(@RequestBody Map<String, Object> paramsMap) throws Exception {
        return Response.ok(this.service.getSqlParams(paramsMap));
    }

    /**
     * 测试sql是否能执行
     *
     * @param paramsMap
     * @return
     * @throws Exception
     */
    @PostMapping("/testExecuteSql")
    public Response testExecuteSql(@RequestBody Map<String, Object> paramsMap) throws Exception {
        return Response.ok(this.service.testExecuteSql(paramsMap));
    }

    /**
     * 获取所有报表
     *
     * @param paramsMap
     * @return
     * @throws Exception
     */
    @PostMapping("/getReport")
    public Response getReport(@RequestBody Map<String, Object> paramsMap) throws Exception {
        return Response.ok(this.service.getReport(paramsMap));
    }

    /**
     * 批量发布报表
     *
     * @param paramsMap
     * @return
     * @throws Exception
     */
    @PostMapping("/mdfReportPublishBatch")
    public Response mdfReportPublish(@RequestBody Map<String, Object> paramsMap) throws Exception {
        this.service.mdfReportPublish(paramsMap);
        return Response.ok();
    }

    /**
     * 批量删除报表
     *
     * @param paramsMap
     * @return
     * @throws Exception
     */
    @PostMapping("/delReportBatch")
    public Response delReport(@RequestBody Map<String, Object> paramsMap) throws Exception {
        this.service.delReport(paramsMap);
        return Response.ok();
    }

    /**
     * 查询条件-获取所有报表
     *
     * @return
     * @throws Exception
     */
    @PostMapping("/getReportList")
    public Response getReportList(@RequestBody Map<String, Object> paramsMap) throws Exception {
        return Response.ok(this.service.getReportList(paramsMap));
    }

    /**
     * 保存报表
     *
     * @return
     * @throws Exception
     */
    @PostMapping("/addReport")
    public Response addReport(@RequestBody Map<String, Object> paramsMap) throws Exception {
        return Response.ok(this.service.addReport(paramsMap));
    }

    /**
     * 保存并发布报表
     *
     * @return
     * @throws Exception
     */
    @PostMapping("/publishReport")
    public Response publishReport(@RequestBody Map<String, Object> paramsMap) throws Exception {
        return Response.ok(this.service.publishReport(paramsMap));
    }

    /**
     * 查询所有菜单,包括绑定报表的菜单
     *
     * @return
     * @throws Exception
     */
    @PostMapping("/getMenuList")
    public Response getMenuList(@RequestBody Map<String, Object> paramsMap) throws Exception {
        return Response.ok(this.service.getMenuList(paramsMap));
    }

    /**
     * 将报表绑定到菜单
     *
     * @return
     * @throws Exception
     */
    @PostMapping("/reportBindMenu")
    public Response reportBindMenu(@RequestBody Map<String, Object> paramsMap) throws Exception {
        this.service.reportBindMenu(paramsMap);
        return Response.ok();
    }

    /**
     * 获取该报表的历史版本列表
     *
     * @return
     * @throws Exception
     */
    @PostMapping("/getReportHistoryList")
    public Response getReportHistoryList(@RequestBody Map<String, Object> paramsMap) throws Exception {
        return Response.ok(this.service.getReportHistoryList(paramsMap));
    }

    /**
     * 获取该报表的某个历史版本详情
     *
     * @return
     * @throws Exception
     */
    @PostMapping("/getReportHistoryDetail")
    public Response getReportHistoryDetail(@RequestBody Map<String, Object> paramsMap) throws Exception {
        return Response.ok(this.service.getReportHistoryDetail(paramsMap));
    }
    /**
     * 获取某报表详情
     *
     * @return
     * @throws Exception
     */
    @PostMapping("/getReportDetail")
    public Response getReportDetail(@RequestBody Map<String, Object> paramsMap) throws Exception {
        return Response.ok(this.service.getReportDetail(paramsMap));
    }

    /**
     * 执行sql,报表展示
     *
     * @return
     * @throws Exception
     */
    @PostMapping("/executeSql")
    public Response executeSql(@RequestBody Map<String, Object> paramsMap) throws Exception {
        return Response.ok(this.service.searchReport(paramsMap));
    }

    /**
     * 获取图表数据
     *
     * @return
     * @throws Exception
     */
    @PostMapping("/getCharts")
    public Response getCharts(@RequestBody Map<String, Object> paramsMap) throws Exception {
        return Response.ok(this.service.getCharts(paramsMap));
    }


    /**
     *  根据报表id获取该报表sql中的系统固定变量
     *
     * @return
     * @throws Exception
     */
    @PostMapping("/getSysVarById")
    public Response getSysVarById(@RequestBody Map<String, Object> paramsMap) throws Exception {
        return Response.ok(this.service.getSysVarById(paramsMap));
    }

    /**
     *  根据报表名称获取所有报表
     *
     * @return
     * @throws Exception
     */
    @PostMapping("/getReportListByName")
    public Response getReportListByName(@RequestBody Map<String, Object> paramsMap) throws Exception {
        return Response.ok(this.service.getReportListByName(paramsMap));
    }
    /**
     *  根据报表id数组获取所有报表名称
     *
     * @return
     * @throws Exception
     */
    @PostMapping("/getReportListByIdArr")
    public Response getReportListByIdArr(@RequestBody Map<String, Object> paramsMap) throws Exception {
        return Response.ok(this.service.getReportListByIdArr(paramsMap));
    }

    /**
     *  根据时间变量解析成时间
     *
     * @return
     * @throws Exception
     */
    @PostMapping("/getTimeVar")
    public Response getTimeVar(@RequestBody Map<String, Object> paramsMap) throws Exception {
        return Response.ok(this.service.getTimeVar(paramsMap));
    }

    /**
     *  测试
     *
     * @return
     * @throws Exception
     */
    @PostMapping("/test")
    public Response test(@RequestBody Map<String, Object> paramsMap) throws Exception {
        return Response.ok(this.reportDesignMapper.test(paramsMap));
    }



}
