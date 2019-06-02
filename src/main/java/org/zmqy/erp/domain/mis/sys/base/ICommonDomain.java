package org.zmqy.erp.domain.mis.sys.base;

import org.zmqy.erp.domain.mis.IBaseDomain;
import org.zmqy.erp.model.mis.bo.sys.base.CommonBo;

import java.util.Date;
import java.util.List;

public interface ICommonDomain extends IBaseDomain<CommonBo> {
    public String save(CommonBo commonBo, String operUserId, Date operTime) throws Exception;

    public void delCommonByComType(String comTypeId, List<String> comIdList, String operUserId, Date operTime) throws Exception;
}
