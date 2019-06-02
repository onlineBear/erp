package org.zmqy.erp.model.mis.entity.rpt;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tReportDesignHistory")
public class ReportDesignHistory {
    private String id;

    private String reportId;
    public static final String REPORTID = "reportId";

    private String reportName;

    private String publishStatus;

    private String category;

    private String reportType;

    private String chartType;

    private String reportState;

    private String sql;

    private Integer version;

    private String createUserId;

    private Date createTime;

    private String createStore;

    private String createStoreId;

    private Date lastUpdateTime;

    private String config;
}