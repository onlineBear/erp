package org.zmqy.erp.domain.mis.base.vendor;

import org.zmqy.erp.model.mis.bo.base.vendor.VendorBo;

import java.util.Date;
import java.util.List;

public interface IVendorDomain{
    public String add(VendorBo bo, String operUserId, Date operTime) throws Exception;
    public void batchAdd(List<VendorBo> boList, String operUserId, Date operTime) throws Exception;

    public void mdfById(VendorBo bo, String operUserId, Date operTime) throws Exception;

    public void delVendorCertif(String vendorId, List<String> vendorCertifIdList, String operUserId, Date operTime) throws Exception;
}

