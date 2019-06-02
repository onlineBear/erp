package org.zmqy.erp.model.mis.bo.sys.base;

import lombok.Data;
import org.springframework.cglib.beans.BeanCopier;
import org.zmqy.erp.model.mis.entity.sys.base.CommonLang;

import java.util.Date;

@Data
public class CommonLangBo {
    private String id;

    private String comId;

    private String langId;

    private String comName;

    private String comDesc;

    private String createUserId;

    private Date createTime;

    private Date lastUpdateTime;

    private static final BeanCopier bo2entityCopier = BeanCopier.create(CommonLangBo.class, CommonLang.class, false);

    public static CommonLang bo2entity(CommonLangBo bo){
        if (bo == null){
            return null;
        }

        CommonLang entity = new CommonLang();

        bo2entityCopier.copy(bo, entity, null);

        return entity;
    }
}
