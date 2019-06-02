package org.zmqy.erp.mapper.mis.biz.log;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import org.zmqy.erp.model.mis.entity.log.OperationLog;

@Repository
public interface OperationLogMapper extends BaseMapper<OperationLog> {
}
