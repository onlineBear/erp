package org.zmqy.erp.model.mis.bo.sys.flow;

import lombok.Data;
import org.springframework.cglib.beans.BeanCopier;
import org.zmqy.erp.model.mis.flow.Flow;
import org.zmqy.erp.model.mis.flow.FlowDtlLang;

import java.util.Date;

@Data
public class FlowDtlLangBo {
    private String id;

    private String langId;

    private String flowDtlId;

    private String flowDtlName;

    private String flowDesc;

    private String createUserId;

    private Date createUserTime;

    private Date lastUpdateTime;

    private static final BeanCopier bo2entityCopier = BeanCopier.create(FlowDtlLangBo.class, FlowDtlLang.class, false);

    public static FlowDtlLang bo2entity(FlowDtlLangBo bo){
        if (bo == null){
            return null;
        }

        FlowDtlLang entity = new FlowDtlLang();

        bo2entityCopier.copy(bo, entity, null);

        return entity;
    }


}
