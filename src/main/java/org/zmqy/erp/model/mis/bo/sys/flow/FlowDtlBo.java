package org.zmqy.erp.model.mis.bo.sys.flow;

import lombok.Data;
import org.springframework.cglib.beans.BeanCopier;
import org.zmqy.erp.model.mis.flow.FlowDtl;

import java.util.Date;
import java.util.List;

@Data
public class FlowDtlBo {
    private String id;

    private String flowId;

    private Integer seq;

    private Boolean areLastLevel;

    private String createUserId;

    private Date createUserTime;

    private Date lastUpdateTime;

    List<FlowDtlLangBo> flowDtlLangBoList;

    private static final BeanCopier bo2entityCopier = BeanCopier.create(FlowDtlBo.class, FlowDtl.class, false);

    public static FlowDtl bo2entity(FlowDtlBo bo){
        if (bo == null){
            return null;
        }

        FlowDtl entity = new FlowDtl();

        bo2entityCopier.copy(bo, entity, null);

        return entity;
    }
}
