package org.anson.miniProject.core.domain.sys.permission.impl;

import org.anson.miniProject.core.domain.sys.permission.IResourceDomain;
import org.anson.miniProject.core.domain.sys.permission.IRoleResourceDomain;
import org.anson.miniProject.core.model.bo.sys.permission.resource.ResourceBO;
import org.anson.miniProject.core.model.param.sys.permission.resource.AddResourceParam;
import org.anson.miniProject.core.model.param.sys.permission.resource.MdfResourceParam;
import org.anson.miniProject.core.model.po.sys.permission.Resource;
import org.anson.miniProject.core.repository.sys.permission.impl.ResourceRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
@Transactional(rollbackFor = Exception.class)
public class ResourceDomain implements IResourceDomain {
    @Autowired
    private ResourceRep rep;
    @Autowired
    private IRoleResourceDomain roleResourceDomain;

    @Override
    public String add(AddResourceParam param, String operUserId, Date operTime) throws Exception {
        ResourceBO bo = AddResourceParam.toBO(param);

        Resource po = ResourceBO.toResource(bo);
        return this.rep.insert(po, operUserId, operTime);
    }

    @Override
    public void mdf(MdfResourceParam param, String operUserId, Date operTime) throws Exception {
        ResourceBO bo = MdfResourceParam.toBO(param);

        Resource po = ResourceBO.toResource(bo);
        this.rep.update(po, operUserId, operTime);
    }

    @Override
    public void del(String id, String operUserId, Date operTime) throws Exception {
        // 删除 resource
        this.rep.del(id, operUserId, operTime);

        // 删除 roleResource
        this.roleResourceDomain.delByResource(id, operUserId, operTime);
    }
}
