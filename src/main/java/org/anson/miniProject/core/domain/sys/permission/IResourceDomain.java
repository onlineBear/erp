package org.anson.miniProject.core.domain.sys.permission;

import org.anson.miniProject.core.model.param.sys.permission.resource.AddResourceParam;
import org.anson.miniProject.core.model.param.sys.permission.resource.MdfResourceParam;

import java.util.Date;

public interface IResourceDomain {
    String add(AddResourceParam param, String operUserId, Date operTime) throws Exception;
    void mdf(MdfResourceParam param, String operUserId, Date operTime) throws Exception;
    void del(String id, String operUserId, Date operTime) throws Exception;
}
