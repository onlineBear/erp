package org.zmqy.erp.mapper.mis.pc.module.sys.base;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CommonTypeModuleMapper {
    // 列表查询
    public List<Map> getList(Map paramsMap);
    public Long getListTotal(Map paramsMap);
    public Map getListSum(Map paramsMap);

    // 详情查询
    public Map getCommonType(@Param("comTypeId")String comTypeId, @Param("langId")String langId);
    public List<Map> getCommonList(@Param("comTypeId")String comTypeId, @Param("langId")String langId);
}
