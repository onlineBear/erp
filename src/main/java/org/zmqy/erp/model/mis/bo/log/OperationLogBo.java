package org.zmqy.erp.model.mis.bo.log;

import lombok.Data;
import org.springframework.cglib.beans.BeanCopier;
import org.zmqy.erp.model.mis.entity.log.OperationLog;

import java.util.Date;

@Data
public class OperationLogBo {
    private String id;

    private String recordId;

    private Date operTime;

    private String operUserId;

    private String ipv4;

    private String operTypeNo;

    private String operMenuNo;

    private String url;

    private String createUserId;

    private Date createTime;

    private Date lastUpdateTime;

    private static final BeanCopier bo2entityCopier = BeanCopier.create(OperationLogBo.class, OperationLog.class, false);

    public static OperationLog bo2entity(OperationLogBo bo){
        if (bo == null){
            return null;
        }

        OperationLog entity = new OperationLog();

        bo2entityCopier.copy(bo, entity, null);

        return entity;
    }
}
