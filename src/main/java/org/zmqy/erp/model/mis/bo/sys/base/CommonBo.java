package org.zmqy.erp.model.mis.bo.sys.base;

import lombok.Data;
import org.springframework.cglib.beans.BeanCopier;
import org.zmqy.erp.model.mis.entity.sys.base.Common;

import java.util.Date;
import java.util.List;

@Data
public class CommonBo {
    private String id;

    private String comNo;

    private String comTypeId;

    private Double num1;

    private String text1;

    private String createUserId;

    private Date createTime;

    private Date lastUpdateTime;

    private List<CommonLangBo> commonLangBoList;

    private static final BeanCopier bo2entityCopier = BeanCopier.create(CommonBo.class, Common.class, false);

    public static Common bo2entity(CommonBo bo){
        if (bo == null){
            return null;
        }

        Common entity = new Common();

        bo2entityCopier.copy(bo, entity, null);

        return entity;
    }
}
