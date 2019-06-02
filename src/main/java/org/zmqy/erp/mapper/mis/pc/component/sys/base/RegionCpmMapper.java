package org.zmqy.erp.mapper.mis.pc.component.sys.base;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface RegionCpmMapper {

    public List<Map<String, String>> getList(@Param("langId")String langId);
}
