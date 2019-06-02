package org.zmqy.erp.model.mis.entity.rpt;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.cglib.beans.BeanCopier;

import java.util.Date;

@Data
@TableName("tReportDesign")
public class ReportDesign {
    private String id;

    private String reportName;

    private String publishStatus;

    private String category;

    private String reportType;

    private String chartType;

    private String reportState;

    private String sql;

    private String createUserId;

    private Date createTime;

    private String createStore;

    private String createStoreId;

    private Date lastUpdateTime;

    private String config;

    private static final BeanCopier bo2entityCopier = BeanCopier.create(ReportDesign.class, ReportDesignHistory.class, false);

    public static ReportDesignHistory toReportHistory(ReportDesign bo){
        if (bo == null){
            return null;
        }

        ReportDesignHistory entity = new ReportDesignHistory();

        bo2entityCopier.copy(bo, entity, null);

        return entity;
    }
}