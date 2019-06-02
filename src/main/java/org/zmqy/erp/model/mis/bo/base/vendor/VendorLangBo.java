package org.zmqy.erp.model.mis.bo.base.vendor;

import lombok.Data;
import org.springframework.cglib.beans.BeanCopier;
import org.zmqy.erp.model.mis.entity.base.vendor.VendorCertif;
import org.zmqy.erp.model.mis.entity.base.vendor.VendorLang;

import java.util.Date;

@Data
public class VendorLangBo {
    private String id;

    private String langId;

    private String vendorId;

    private String vendorName;

    private String vendorShortName;

    private String createUserId;

    private Date createTime;

    private Date lastUpdateTime;

    private static final BeanCopier bo2entityCopier = BeanCopier.create(VendorLangBo.class, VendorLang.class, false);

    public static VendorLang bo2entity(VendorLangBo bo){
        if (bo == null){
            return null;
        }

        VendorLang entity = new VendorLang();

        bo2entityCopier.copy(bo, entity, null);

        return entity;
    }
}
