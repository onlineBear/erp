package org.zmqy.erp.domain.mis.rpt.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.zmqy.erp.domain.mis.rpt.IReportDesignDomain;
import org.zmqy.erp.mapper.mis.biz.rpt.ReportDesignHistoryMapper;
import org.zmqy.erp.mapper.mis.biz.rpt.ReportDesignMapper;
import org.zmqy.erp.mapper.mis.biz.rpt.TemplatePrintMapper;
import org.zmqy.erp.mapper.mis.pc.module.rpt.ReportDesignModuleMapper;
import org.zmqy.erp.model.mis.bo.rpt.ReportDesignBo;
import org.zmqy.erp.model.mis.entity.rpt.ReportDesign;
import org.zmqy.erp.model.mis.entity.rpt.ReportDesignHistory;
import org.zmqy.erp.model.mis.entity.rpt.ReportRelation;
import org.zmqy.erp.model.mis.entity.rpt.TemplatePrint;
import org.zmqy.erp.tool.helper.id.IdHelper;
import org.zmqy.erp.tool.util.common.StringUtil;
import org.zmqy.erp.tool.util.file.FileUtil;
import org.zmqy.erp.tool.util.json.JacksonUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 报表设计
 * @Author: lly
 * @Date: 2019-02-14 11:55
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class ReportDesignDomain implements IReportDesignDomain {
    @Autowired
    private ReportDesignMapper mapper;
    @Autowired
    private TemplatePrintMapper templatePrintMapper;
    @Autowired
    private ReportDesignHistoryMapper historyMapper;
    @Autowired
    private ReportDesignModuleMapper reportDesignModuleMapper;


    @Override
    public void publishReport(String reportId, Date operTime) {
        if (StringUtil.isEmpty(reportId)) {
            throw new RuntimeException("参数异常");
        }
        ReportDesign reportDesign = new ReportDesign();
        reportDesign.setId(reportId);
        reportDesign.setPublishStatus("publishStatus-publish");
        reportDesign.setLastUpdateTime(operTime);
        mapper.updateById(reportDesign);
    }

    @Override
    public void delReport(String reportId) throws Exception {
        if (StringUtil.isEmpty(reportId)) {
            return;
        }
        Date date = new Date();
        //删除发布的历史版本
        Map<String, Object> params = new HashMap<>();
        params.put(ReportDesignHistory.REPORTID, reportId);
        historyMapper.deleteByMap(params);


        //删除报表
        mapper.deleteById(reportId);

        //删除报表之后要把报表之间的关系也处理
        //处理该报表下的所有子报表
        List<Map> childList = mapper.getReportRelationById(reportId);

        for (Map childMap : childList) {
            String childReportId = (String) childMap.get("childReportId");
            //关联关系
            String relationType = (String) childMap.get("relationType");
            if ("chain".equals(relationType)) {
                continue;
            }
            ReportDesign reportDesign = mapper.selectById(childReportId);
            if (reportDesign != null) {
                String types = "";
                switch (relationType) {
                    case "father_son":
                        int b = reportDesignModuleMapper.getRelationByChild(childReportId, "father_son");
                        if (b == 1) {
                            //代表只是一种
                            if (reportDesign.getReportType().equals("子报表")) {
                                types = "普通报表";
                            } else {
                                String[] arr = relationType.split(",");
                                for (String str : arr) {
                                    if (!str.equals("子报表")) {
                                        types += str + ",";
                                    }
                                }
                            }
                        } else {
                            types = reportDesign.getReportType();
                        }
                        break;
                    case "group":
                        int a = reportDesignModuleMapper.getRelationByChild(childReportId, "group");
                        if (a > 1) {
                            types = reportDesign.getReportType();
                            break;
                        }
                        //代表只是一种
                        if (reportDesign.getReportType().equals("组合子报表")) {
                            types = "普通报表";
                        } else {
                            String[] arr = reportDesign.getReportType().split(",");
                            for (String str : arr) {
                                if (!str.equals("组合子报表")) {
                                    types += str + ",";
                                }
                            }
                        }
                        break;
                }
                if (types.endsWith(",")) {
                    types = types.substring(0, types.length() - 1);
                }

                ReportDesignBo bo = new ReportDesignBo();
                ReportDesign reportDesign1 = new ReportDesign();
                reportDesign1.setId(reportDesign.getId());
                reportDesign1.setReportType(types);
                bo.setReportDesign(reportDesign1);
                mdfReport(bo, date);


            }
        }
        //删除该报表的所有子关联关系
        reportDesignModuleMapper.delReportRelation(reportId);

        //获取所有以该报表为子报表的报表
        List<Map> parentList = mapper.getReportRelationByChildId(reportId);
        for (Map map : parentList) {
            String parentReportId = (String) map.get("reportId");
            ReportDesign reportDesign = mapper.selectById(parentReportId);
            Map<String, Object> configMap = JacksonUtil.readValue(reportDesign.getConfig(), Map.class);
            List<String> typeList = (List<String>) configMap.get("publishList");
            //要更新的报表类型
            String types = reportDesign.getReportType();
            for (int j = 0; j < typeList.size(); j++) {
                String type = typeList.get(j);
                switch (type) {
                    case "chain":
                        List<Map<String, Object>> columns = (List<Map<String, Object>>) configMap.get("columns");
                        for (Map<String, Object> chainMap : columns) {
                            List<Map<String, Object>> links = (List<Map<String, Object>>) chainMap.get("chainData");

                            int size = links.size();
                            boolean flag = false;
                            for (int i = 0; i < links.size(); i++) {
                                if (links.get(i).get("reportId").equals(reportId)) {
                                    links.remove(i);
                                    i--;
                                    flag = true;
                                }
                            }

                            if (flag) {
                                if (size == 1) {
                                    if (types.equals("链式报表")) {
                                        types = "普通报表";
                                    } else {
                                        String[] arr = types.split(",");
                                        String tempType = "";
                                        for (String str : arr) {
                                            if (!str.equals("链式报表")) {
                                                tempType += str + ",";
                                            }
                                        }
                                        types = tempType;
                                    }

                                    //将类型去除
                                    typeList.remove(j);
                                    j--;

                                }

                            }
                        }
                        break;
                    case "father_son":
                        Map<String, Object> joinReports = (Map<String, Object>) configMap.get("joinReports");
                        if (joinReports.get("reportId").equals(reportId)) {
                            //将配置置为空
                            configMap.put("joinReports", new HashMap<>());
                            //将类型给清除
                            typeList.remove(j);
                            j--;
                            //将类型清掉
                            if (types.equals("父报表")) {
                                types = "普通报表";
                            } else {
                                String[] arr = types.split(",");
                                String tempType = "";
                                for (String str : arr) {
                                    if (!str.equals("父报表")) {
                                        tempType += str + ",";
                                    }
                                }
                                types = tempType;
                            }
                        }

                        break;
                    case "group":
                        List<Map<String, Object>> combinParent = (List<Map<String, Object>>) configMap.get("combinParent");
                        int size = combinParent.size();
                        boolean flag = false;
                        for (int i = 0; i < combinParent.size(); i++) {
                            if (combinParent.get(i).get("reportId").equals(reportId)) {
                                combinParent.remove(i);
                                i--;
                                flag = true;
                            }
                        }
                        if (flag) {
                            if (size == 1) {
                                if (types.equals("组合父报表")) {
                                    types = "普通报表";
                                } else {
                                    String[] arr = types.split(",");
                                    String tempType = "";
                                    for (String str : arr) {
                                        if (!str.equals("组合父报表")) {
                                            tempType += str + ",";
                                        }
                                    }
                                    types = tempType;
                                }

                                //将类型去除
                                typeList.remove(j);
                                j--;

                            }
                        }

                        break;
                }
            }

            String config = JacksonUtil.toJSon(configMap);
            types = types.trim();
            if (types.endsWith(",")) {
                types = types.substring(0, types.length() - 1);
            }
            ReportDesignBo bo = new ReportDesignBo();
            ReportDesign reportDesign1 = new ReportDesign();
            reportDesign1.setId(reportDesign.getId());
            reportDesign1.setReportType(types);
            reportDesign1.setConfig(config);
            bo.setReportDesign(reportDesign1);
            mdfReport(bo, date);

        }


        //删除所有已该报表为子报表的关联关系
        mapper.delReportRelation(reportId);


        //删除报表下面的模板数据
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put(TemplatePrint.REPORTID, reportId);
        templatePrintMapper.deleteByMap(paramsMap);

        //删除该报表下的所有模板文件
        String filePath = this.getClass().getResource("/").getPath();
        if (filePath != null) {
            if (filePath.startsWith("/")) {
                filePath = filePath.substring(1, filePath.length() - 1);
            }
            if (filePath.indexOf("webapps") != -1) {
                filePath = filePath.substring(0, filePath.indexOf("webapps"));
            }
        }
        FileUtil.delDirectory(filePath + "/webapps/reportTemplate/" + reportId);


    }

    @Override
    public String addReport(ReportDesignBo bo, String operUserId, Date operTime) {
        if (bo == null) {
            throw new RuntimeException("数据异常");
        }
        ReportDesign entity = bo.getReportDesign();
        if (entity == null) {
            throw new RuntimeException("数据异常");
        }
        if (StringUtil.isEmpty(entity.getReportName())) {
            throw new RuntimeException("请输入报表名称");
        }
        if (StringUtil.isEmpty(entity.getPublishStatus())) {
            throw new RuntimeException("请设置发布状态");
        }
        if (entity.getCategory() == null) {
            entity.setCategory("");
        }
        if (StringUtil.isEmpty(entity.getReportType())) {
            throw new RuntimeException("请设置报表类型");
        }
        if (entity.getChartType() == null) {
            entity.setChartType("");
        }
        if (entity.getReportState() == null) {
            entity.setReportState("");
        }
        if (StringUtil.isEmpty(entity.getSql())) {
            throw new RuntimeException("请输入sql语句");
        }
        if (StringUtil.isEmpty(entity.getConfig())) {
            throw new RuntimeException("未配置报表");
        }
        if (StringUtil.isEmpty(entity.getCreateStore())) {
            throw new RuntimeException("所属组织为空");
        }
        if (StringUtil.isEmpty(entity.getCreateStoreId())) {
            throw new RuntimeException("所属组织为空");
        }

        entity.setId(IdHelper.nextId());
        entity.setCreateUserId(operUserId);
        entity.setCreateTime(operTime);
        entity.setLastUpdateTime(operTime);
        mapper.insert(entity);

        //新增完报表之后需要解析配置,将数据补充到关联关系表
        List<ReportRelation> list = bo.getReportRelationList();
        if (list != null) {
            for (ReportRelation reportRelation : list) {
                reportRelation.setReportId(entity.getId());
                if (StringUtil.isEmpty(reportRelation.getChildReportId())) {
                    throw new RuntimeException("数据异常");
                }
                if (StringUtil.isEmpty(reportRelation.getRelationState())) {
                    reportRelation.setRelationState("");
                }
                if (StringUtil.isEmpty(reportRelation.getRelationType())) {
                    reportRelation.setRelationType("");
                }
                mapper.saveReportRelation(reportRelation);

            }
        }


        return entity.getId();

    }

    @Override
    public void mdfReport(ReportDesignBo bo, Date operTime) {
        if (bo == null) {
            return;
        }
        ReportDesign entity = bo.getReportDesign();
        if (entity == null) {
            return;
        }
        if (StringUtil.isEmpty(entity.getId())) {
            return;
        }
        entity.setLastUpdateTime(operTime);
        mapper.updateById(entity);

        //修改之后还要将关联关系处理清楚
        List<ReportRelation> list = bo.getReportRelationList();
        if (list != null) {
            for (ReportRelation reportRelation : list) {
                reportRelation.setReportId(entity.getId());
                if (StringUtil.isEmpty(reportRelation.getChildReportId())) {
                    throw new RuntimeException("数据异常");
                }
                if (StringUtil.isEmpty(reportRelation.getRelationState())) {
                    reportRelation.setRelationState("");
                }
                if (StringUtil.isEmpty(reportRelation.getRelationType())) {
                    reportRelation.setRelationType("");
                }
                mapper.saveReportRelation(reportRelation);

            }
        }

    }

    @Override
    public void addReportHistory(ReportDesignHistory reportHistory, String operUserId, Date operTime) {
        if (reportHistory == null) {
            throw new RuntimeException("数据异常");
        }
        if (StringUtil.isEmpty(reportHistory.getReportName())) {
            throw new RuntimeException("请输入报表名称");
        }
        if (StringUtil.isEmpty(reportHistory.getPublishStatus())) {
            throw new RuntimeException("请设置发布状态");
        }
        if (reportHistory.getCategory() == null) {
            reportHistory.setCategory("");
        }
        if (StringUtil.isEmpty(reportHistory.getReportType())) {
            throw new RuntimeException("请设置报表类型");
        }
        if (reportHistory.getChartType() == null) {
            reportHistory.setChartType("");
        }
        if (reportHistory.getReportState() == null) {
            reportHistory.setReportState("");
        }
        if (StringUtil.isEmpty(reportHistory.getSql())) {
            throw new RuntimeException("请输入sql语句");
        }
        if (StringUtil.isEmpty(reportHistory.getConfig())) {
            throw new RuntimeException("未配置报表");
        }
        if (StringUtil.isEmpty(reportHistory.getCreateStore())) {
            throw new RuntimeException("所属组织为空");
        }
        if (StringUtil.isEmpty(reportHistory.getCreateStoreId())) {
            throw new RuntimeException("所属组织为空");
        }
        if (StringUtil.isEmpty(reportHistory.getReportId())) {
            throw new RuntimeException("历史版本保存失败");
        }

        reportHistory.setCreateUserId(operUserId);
        reportHistory.setCreateTime(operTime);
        reportHistory.setLastUpdateTime(operTime);
        reportHistory.setVersion(historyMapper.getMaxVersion(reportHistory.getReportId()) + 1);
        reportHistory.setId(IdHelper.nextId());
        historyMapper.insert(reportHistory);


    }

    @Override
    public ReportDesign selectById(String reportId) {
        return mapper.selectById(reportId);
    }

}
