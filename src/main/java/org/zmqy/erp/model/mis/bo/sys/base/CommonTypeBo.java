package org.zmqy.erp.model.mis.bo.sys.base;

import lombok.Data;
import org.springframework.cglib.beans.BeanCopier;
import org.zmqy.erp.model.mis.entity.sys.base.CommonType;

import java.util.Date;
import java.util.List;

@Data
public class CommonTypeBo {
    private String id;

    private String comTypeNo;

    private Boolean canAdd;

    private Boolean canMdf;

    private Boolean canDel;

    private String createUserId;

    private Date createTime;

    private Date lastUpdateTime;

    private List<CommonTypeLangBo> commonTypeLangBoList;

    private List<CommonBo> commonBoList;

    private static final BeanCopier bo2entityCopier = BeanCopier.create(CommonTypeBo.class, CommonType.class, false);

    public static CommonType bo2entity(CommonTypeBo bo){
        if (bo == null){
            return null;
        }

        CommonType entity = new CommonType();

        bo2entityCopier.copy(bo, entity, null);

        return entity;
    }
}
