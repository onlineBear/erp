package org.zmqy.erp.mapper.mis.pc.component.sys.base;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ResourceCpmMapper {
    public List<Map> getMenuResource(@Param("userId")String userId, @Param("langId")String langId,
                                     @Param("menuNo")String menuNo);
}
