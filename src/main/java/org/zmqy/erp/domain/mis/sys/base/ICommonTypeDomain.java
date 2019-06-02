package org.zmqy.erp.domain.mis.sys.base;

import org.zmqy.erp.model.mis.bo.sys.base.CommonTypeBo;
import org.zmqy.erp.model.mis.entity.sys.base.CommonType;

import java.util.Date;
import java.util.List;

public interface ICommonTypeDomain {
    public String add(CommonTypeBo bo, String operUserId, Date operTime) throws Exception;

    public String mdfById(CommonTypeBo bo, String operUserId, Date operTime) throws Exception;

    public void delCommon(String comTypeId, List<String> comIdList, String operUserId, Date operTime) throws Exception;


    public CommonType getById(String comTypeId);
    public String save(CommonTypeBo bo, String operUserId, Date operTime) throws Exception;
    public Boolean existsCommonTypeId(String commonTypeId) throws Exception;
}
