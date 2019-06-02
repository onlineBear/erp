package org.zmqy.erp.mapper.mis.pc.component.sys.columnInfo;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Auther: lly
 * @Date: 2018-12-18 11:44
 */
@Mapper
public interface ColumnCpmMapper {
    List<Map> getUseHeadColumnByMenuNo(Map<String, Object> paramsMap);

    List<Map> getUseDelColumnConfigByMenuNo(Map<String, Object> paramsMap);

    List<String> getDtlNoList(Map<String, Object> paramsMap);

    List<String> getDtlNoListByMenuId(Map<String, Object> paramsMap);

    List<Map<String, String>> getFormulaList(@Param("menuNo") String menuNo, @Param("pageTypeNo") String pageTypeNo, @Param("arrayNo") String arrayNo);

    List<Map> getHeadDetail(Map<String, Object> paramsMap);

    List<Map> getDtlDetail(Map<String, Object> paramsMap);

    Map getDelNameByDtlNo(@Param("menuNo") String menuNo, @Param("pageTypeNo") String pageTypeNo, @Param("arrayNo") String arrayNo, @Param("loginLangId") String loginLangId);
}
