package org.zmqy.erp.mapper.mis.pc.module.sys.columnInfo;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Auther: lly
 * @Date: 2018-12-17 17:21
 */
@Mapper
public interface ColumnModuleMapper {
    List<Map> getColumnAll(Map<String, Object> paramsMap);

    List<Map> getHeadDetail(Map<String,Object> paramsMap);

    List<Map> getDtlDetail(Map<String,Object> paramsMap);

    Integer getLastSeq(Map<String,Object> paramsMap);

}
