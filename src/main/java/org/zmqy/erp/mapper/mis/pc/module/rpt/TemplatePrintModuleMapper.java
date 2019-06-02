package org.zmqy.erp.mapper.mis.pc.module.rpt;

import org.apache.ibatis.annotations.Mapper;import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: lly
 * @Date: 2019-02-19 12:20
 */
@Mapper
public interface TemplatePrintModuleMapper {
    List<Map> getReportTemplate(@Param("reportId")String reportId);
}
