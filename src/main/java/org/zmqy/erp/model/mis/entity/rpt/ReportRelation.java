package org.zmqy.erp.model.mis.entity.rpt;

import lombok.Data;

@Data
public class ReportRelation {
    private String reportId;

    private String childReportId;

    private String relationType;

    private String relationState;
}