package org.zmqy.erp.mapper.mis.pc.module.sys.flow.flowModule;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface FlowModuleMapper {
    List<Map> getFlowList(@Param("langId")String langId);
    List<Map> getFlowDtlList(@Param("flowId")String flowId, @Param("langId")String langId);
    List<Map> getFlowByMenu(@Param("flowId")String flowId, @Param("langId")String langId);
    List<Map> getCheckBill(@Param("billId")String billId, @Param("langId")String langId);
}
