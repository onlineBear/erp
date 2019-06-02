package org.zmqy.erp.mapper.mis.biz.rpt;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import org.zmqy.erp.model.mis.entity.rpt.ReportDesignHistory;

@Repository
public interface ReportDesignHistoryMapper extends BaseMapper<ReportDesignHistory> {
    int getMaxVersion(String reportId);
}