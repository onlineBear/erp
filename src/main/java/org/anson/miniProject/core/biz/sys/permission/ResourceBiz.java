package org.anson.miniProject.core.biz.sys.permission;

import org.anson.miniProject.core.domain.sys.permission.IResourceDomain;
import org.anson.miniProject.core.model.bo.sys.permission.resource.AddResourceBO;
import org.anson.miniProject.core.model.bo.sys.permission.resource.MdfResourceBO;
import org.anson.miniProject.core.model.dmo.sys.permission.resource.AddResourceDMO;
import org.anson.miniProject.core.model.dmo.sys.permission.resource.MdfResourceDMO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
@Transactional(rollbackFor = Exception.class)
public class ResourceBiz {
    @Autowired
    private IResourceDomain domain;

    public String add(AddResourceBO bo, String operUserId, Date operTime) throws Exception{
        AddResourceDMO dmo = AddResourceBO.toAddResourceDMO(bo);
        return this.domain.add(dmo, operUserId, operTime);
    }

    public void mdf(MdfResourceBO bo, String operUserId, Date operTime) throws Exception{
        MdfResourceDMO dmo = MdfResourceBO.toMdfResourceDMO(bo);
        this.domain.mdf(dmo, operUserId, operTime);
    }

    public void del(String id, String operUserId, Date operTime) throws Exception{
        this.domain.del(id, operUserId, operTime);
    }
}
