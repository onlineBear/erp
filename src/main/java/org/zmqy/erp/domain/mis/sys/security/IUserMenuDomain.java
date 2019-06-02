package org.zmqy.erp.domain.mis.sys.security;

import java.util.Date;

public interface IUserMenuDomain {
    public void reBuild(String operUserId, Date operTime) throws Exception;
}
