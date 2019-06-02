package org.zmqy.erp.domain.mis.sys.security;

import org.zmqy.erp.domain.mis.IBaseDomain;
import org.zmqy.erp.model.mis.bo.sys.security.ResourceBo;

import java.util.Date;
import java.util.List;

public interface IResourceDomain extends IBaseDomain<ResourceBo> {
    public String add(ResourceBo bo, String operUserId, Date operTime) throws Exception;

    public void mdfByPPK(ResourceBo bo, String operUserId, Date operTime) throws Exception;

    public String saveById(ResourceBo bo, String operUserId, Date operTime) throws Exception;
    public String saveByPPK(ResourceBo bo, String operUserId, Date operTime) throws Exception;

    public void delByUrl(String resUrl) throws Exception;
    public void delByUrl(List<String> resUrlList) throws Exception;
    public Boolean existsRes(String resId);
}
