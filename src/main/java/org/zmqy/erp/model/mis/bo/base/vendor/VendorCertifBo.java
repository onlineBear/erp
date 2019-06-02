package org.zmqy.erp.model.mis.bo.base.vendor;

import lombok.Data;
import org.springframework.cglib.beans.BeanCopier;
import org.zmqy.erp.model.mis.bo.sys.base.CommonBo;
import org.zmqy.erp.model.mis.entity.base.vendor.VendorCertif;
import org.zmqy.erp.model.mis.entity.sys.base.Common;

import java.util.Date;

@Data
public class VendorCertifBo {
    private String id;

    private String vendorId;

    private String certifTypeNo;

    private String certifNo;

    private Date expirationDate;

    private String picUrl;

    private String createUserId;

    private Date createTime;

    private Date lastUpdateTime;

    private static final BeanCopier bo2entityCopier = BeanCopier.create(VendorCertifBo.class, VendorCertif.class, false);

    public static VendorCertif bo2entity(VendorCertifBo bo){
        if (bo == null){
            return null;
        }

        VendorCertif entity = new VendorCertif();

        bo2entityCopier.copy(bo, entity, null);

        return entity;
    }
}
