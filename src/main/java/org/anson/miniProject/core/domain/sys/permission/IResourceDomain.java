package org.anson.miniProject.core.domain.sys.permission;

import org.anson.miniProject.core.model.dmo.sys.permission.resource.AddResourceDMO;
import org.anson.miniProject.core.model.dmo.sys.permission.resource.MdfResourceDMO;

import java.util.Date;

public interface IResourceDomain {
    String add(AddResourceDMO dmo, String operUserId, Date operTime) throws Exception;
    void mdf(MdfResourceDMO dmo, String operUserId, Date operTime) throws Exception;
    void del(String id, String operUserId, Date operTime) throws Exception;
}
