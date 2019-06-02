package org.zmqy.erp.model.mis.bo.sys.flow;

import lombok.Data;
import org.springframework.cglib.beans.BeanCopier;
import org.zmqy.erp.model.mis.bo.sys.base.CommonBo;
import org.zmqy.erp.model.mis.entity.sys.base.Common;
import org.zmqy.erp.model.mis.flow.Flow;

import java.util.Date;
import java.util.List;

@Data
public class FlowBo {
    private String id;

    private String upFlowId;

    private Integer minLevel;

    private String menuId;

    private String createUserId;

    private Date createUserTime;

    private Date lastUpdateTime;

    List<FlowLangBo> flowLangBoList;
    List<FlowDtlBo> flowDtlBoList;

    private static final BeanCopier bo2entityCopier = BeanCopier.create(FlowBo.class, Flow.class, false);

    public static Flow bo2entity(FlowBo bo){
        if (bo == null){
            return null;
        }

        Flow entity = new Flow();

        bo2entityCopier.copy(bo, entity, null);

        return entity;
    }
}
