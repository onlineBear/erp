package org.anson.miniProject.core.repository.sys.permission;

import org.anson.miniProject.core.mapper.sys.permission.ResourceMapper;
import org.anson.miniProject.core.model.po.sys.permission.Resource;
import org.anson.miniProject.core.repository.IBaseRep;

import java.util.Date;

public interface IResourceRep extends IBaseRep<Resource, ResourceMapper> {
    String insert(Resource po, String operUserId, Date operTime) throws Exception;
    void update(Resource po, String operUserId, Date operTime) throws Exception;
    void del(String id, String operUserId, Date operTime) throws Exception;
}
