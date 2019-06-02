package org.zmqy.erp.domain.mis.rpt.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.zmqy.erp.domain.mis.rpt.ITemplatePrintDomain;
import org.zmqy.erp.mapper.mis.biz.rpt.ReportDesignMapper;
import org.zmqy.erp.mapper.mis.biz.rpt.TemplatePrintMapper;
import org.zmqy.erp.model.mis.entity.rpt.TemplatePrint;
import org.zmqy.erp.tool.helper.id.IdHelper;
import org.zmqy.erp.tool.util.common.StringUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 打印模板管理
 * @Author: lly
 * @Date: 2019-02-19 14:35
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class TemplatePrintDomain implements ITemplatePrintDomain {
    @Autowired
    private TemplatePrintMapper mapper;
    @Autowired
    private ReportDesignMapper reportDesignMapper;

    @Override
    public void updateById(TemplatePrint entity, Date operTime) {
        if (StringUtil.isEmpty(entity.getId())) {
            return;
        }
        if ("".equals(entity.getScaling())) {
            entity.setScaling(null);
        }
        if ("".equals(entity.getCodePrintModel())) {
            entity.setCodePrintModel(null);
        }
        if (entity.getCodeNumber() != null) {
            if (entity.getCodeNumber() <= 0) {
                entity.setCodeNumber(null);
            }

        }
        if ("".equals(entity.getCodeModel())) {
            entity.setCodeModel(null);
        }
        if (entity.getWidth() != null) {
            if (entity.getWidth() < 0) {
                entity.setWidth(0);
            }
        }
        if (entity.getHeight() != null) {
            if (entity.getHeight() < 0) {
                entity.setHeight(0);
            }
        }

        entity.setReportId(null);
        entity.setTemplateFileName(null);
        entity.setTemplateName(null);
        entity.setCreateTime(null);
        entity.setCreateUserId(null);

        entity.setLastUpdateTime(operTime);

        mapper.updateById(entity);

    }

    public void add(TemplatePrint entity, String operUserId, Date operTime) {
        if (StringUtil.isEmpty(entity.getReportId())) {
            throw new RuntimeException("报表id为空");
        }
        if (reportDesignMapper.selectById(entity.getReportId()) == null) {
            throw new RuntimeException("绑定报表不存在");
        }
        if (StringUtil.isEmpty(entity.getTemplateFileName())) {
            throw new RuntimeException("请设置报表文件名");
        }
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put(TemplatePrint.REPORTID, entity.getReportId());
        paramsMap.put(TemplatePrint.TEMPLATEFILENAME, entity.getTemplateFileName());
        List<TemplatePrint> templatePrintList = mapper.selectByMap(paramsMap);
        if (templatePrintList.size() > 0) {
            throw new RuntimeException("已存在此文件");
        }

        if (StringUtil.isEmpty(entity.getTemplateName())) {
            throw new RuntimeException("请设置报表名称");
        }
        if (StringUtil.isEmpty(entity.getScaling())) {
            throw new RuntimeException("请设置缩放比例");
        }
        if (StringUtil.isEmpty(entity.getCodePrintModel())) {
            throw new RuntimeException("请设置条码打印模式");
        }
        if (entity.getCodeNumber() <= 0) {
            entity.setCodeNumber(1);
        }
        if (StringUtil.isEmpty(entity.getCodeModel())) {
            throw new RuntimeException("请设置条码生成模式");
        }
        if (StringUtil.isEmpty(entity.getFilePath())) {
            throw new RuntimeException("文件路径为空");
        }
        if (entity.getWidth() == null) {
            entity.setWidth(0);
        }
        if (entity.getWidth() < 0) {
            entity.setWidth(0);
        }
        if (entity.getHeight() == null) {
            entity.setHeight(0);
        }
        if (entity.getHeight() < 0) {
            entity.setHeight(0);
        }

        entity.setId(IdHelper.nextId());
        entity.setCreateTime(operTime);
        entity.setCreateUserId(operUserId);
        entity.setLastUpdateTime(operTime);
        if(StringUtil.isEmpty(operUserId)){
            entity.setCreateUserId("admin");
        }
        mapper.insert(entity);


    }

    @Override
    public void del(String id) {
        mapper.deleteById(id);
    }

    @Override
    public TemplatePrint selectById(String id) {
        return mapper.selectById(id);
    }


}
