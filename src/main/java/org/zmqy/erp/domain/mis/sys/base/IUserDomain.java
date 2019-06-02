package org.zmqy.erp.domain.mis.sys.base;

import org.zmqy.erp.domain.mis.IBaseDomain;
import org.zmqy.erp.model.mis.bo.sys.base.UserBo;

public interface IUserDomain extends IBaseDomain<UserBo> {
    public String verify(String userNo, String password) throws Exception;
}
