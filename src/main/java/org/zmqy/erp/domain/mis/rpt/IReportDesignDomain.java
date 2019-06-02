package org.zmqy.erp.domain.mis.rpt;

import org.zmqy.erp.model.mis.bo.rpt.ReportDesignBo;
import org.zmqy.erp.model.mis.entity.rpt.ReportDesign;
import org.zmqy.erp.model.mis.entity.rpt.ReportDesignHistory;

import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Author: lly
 * @Date: 2019-02-14 11:54
 */
public interface IReportDesignDomain {
    void publishReport(String reportId, Date operTime);

    void delReport(String reportId) throws Exception;

    String addReport(ReportDesignBo bo, String operUserId, Date operTime);

    void mdfReport(ReportDesignBo bo, Date operTime);

    void addReportHistory(ReportDesignHistory reportHistory, String operUserId, Date operTime);

    ReportDesign selectById(String reportId);
}
