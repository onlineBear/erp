package org.zmqy.erp.model.mis.bo.sys.flow;

import lombok.Data;
import org.springframework.cglib.beans.BeanCopier;
import org.zmqy.erp.model.mis.flow.FlowLog;

import java.util.Date;

@Data
public class FlowLogBo {
    private String id;

    private String userId;

    private String ip;

    private Date checkTime;

    private String operNo;

    private String flowId;

    private String billId;

    private Integer level;

    private String createUserId;

    private Date createUserTime;

    private Date lastUpdateTime;

    private static final BeanCopier bo2entityCopier = BeanCopier.create(FlowLogBo.class, FlowLog.class, false);

    public static FlowLog bo2entity(FlowLogBo bo){
        if (bo == null){
            return null;
        }

        FlowLog entity = new FlowLog();

        bo2entityCopier.copy(bo, entity, null);

        return entity;
    }
}
