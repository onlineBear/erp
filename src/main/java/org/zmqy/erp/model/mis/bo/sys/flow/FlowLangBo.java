package org.zmqy.erp.model.mis.bo.sys.flow;

import lombok.Data;
import org.springframework.cglib.beans.BeanCopier;
import org.zmqy.erp.model.mis.flow.Flow;
import org.zmqy.erp.model.mis.flow.FlowLang;

import java.util.Date;

@Data
public class FlowLangBo {
    private String id;

    private String flowId;

    private String langId;

    private String flowName;

    private String description;

    private String createUserId;

    private Date createUserTime;

    private Date lastUpdateTime;

    private static final BeanCopier bo2entityCopier = BeanCopier.create(FlowLangBo.class, FlowLang.class, false);

    public static FlowLang bo2entity(FlowLangBo bo){
        if (bo == null){
            return null;
        }

        FlowLang entity = new FlowLang();

        bo2entityCopier.copy(bo, entity, null);

        return entity;
    }
}
