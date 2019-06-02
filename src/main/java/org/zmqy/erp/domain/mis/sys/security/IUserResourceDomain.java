package org.zmqy.erp.domain.mis.sys.security;

import java.util.Date;

public interface IUserResourceDomain {
    public void reBuild(String operUserId, Date operTime) throws Exception;
}
