package org.zmqy.erp.mapper.mis.pc.module.rpt;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.zmqy.erp.model.mis.entity.rpt.ReportRelation;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: lly
 * @Date: 2019-02-13 14:23
 */
@Mapper
public interface ReportDesignModuleMapper {
    List<Map> getReport(@Param("paramsMap") Map<String,Object> paramsMap, @Param("list") List<String> list);

    List<Map> getReportList();

    List<Map> getParentReportAll(String id);

    List<Map> getMenuList(String langId);

    int unbundMenu(String reportId);

    int reportBindMenu(@Param("reportId") String reportId,@Param("menuId") String menuId);

    Map getReportById(String reportId);

    List<Map> getReportHistoryList(String reportId);

    List<Map> getReportListByName(@Param("reportName") String reportName, @Param("parent")String parent, @Param("combinParent")String combinParent);

    void delReportRelation(@Param("reportId")String reportId);

    List<Map> getReportRelation(@Param("reportId")String reportId);

    int getRelationByChild(@Param("childId")String childId, @Param("type")String type);

    List<Map> getRelationByChildId(@Param("childId")String childId, @Param("type")String type);
}
