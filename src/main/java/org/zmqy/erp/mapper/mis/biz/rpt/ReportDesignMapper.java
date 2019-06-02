package org.zmqy.erp.mapper.mis.biz.rpt;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;import org.springframework.stereotype.Repository;
import org.zmqy.erp.model.mis.entity.rpt.ReportDesign;
import org.zmqy.erp.model.mis.entity.rpt.ReportRelation;

import java.util.List;
import java.util.Map;

@Repository
public interface ReportDesignMapper extends BaseMapper<ReportDesign> {
    void saveReportRelation(ReportRelation model);

    List<Map> getReportRelationById(@Param("reportId")String reportId);

    List<Map> getReportRelationByChildId(@Param("childReportId")String childReportId);

    void delReportRelation(@Param("childReportId")String childReportId);

    String test (Map<String,Object> paramsMap);
}