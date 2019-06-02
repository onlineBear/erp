package org.zmqy.erp.model.mis.bo.rpt;

import lombok.Data;
import org.zmqy.erp.model.mis.entity.rpt.ReportDesign;
import org.zmqy.erp.model.mis.entity.rpt.ReportRelation;

import java.util.List;

/**
 * @Description:
 * @Author: lly
 * @Date: 2019-03-06 12:17
 */
@Data
public class ReportDesignBo {
    private ReportDesign reportDesign;

    private List<ReportRelation> reportRelationList;
}
