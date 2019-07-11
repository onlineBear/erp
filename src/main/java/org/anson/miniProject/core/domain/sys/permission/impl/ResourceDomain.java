package org.anson.miniProject.core.domain.sys.permission.impl;

import org.anson.miniProject.core.domain.sys.IDelRecordDomain;
import org.anson.miniProject.core.domain.sys.permission.IResourceDomain;
import org.anson.miniProject.core.model.dmo.sys.DelRecordDmo;
import org.anson.miniProject.core.model.dmo.sys.permission.resource.AddResourceDMO;
import org.anson.miniProject.core.model.dmo.sys.permission.resource.MdfResourceDMO;
import org.anson.miniProject.core.model.po.sys.permission.Resource;
import org.anson.miniProject.core.repository.sys.permission.ResourceRep;
import org.anson.miniProject.framework.jackson.Jackson;
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
    private IDelRecordDomain delRecordDomain;
    @Autowired
    private Jackson jackson;

    @Override
    public String add(AddResourceDMO dmo, String operUserId, Date operTime) throws Exception {
        Resource po = AddResourceDMO.toResource(dmo);
        return this.rep.insert(po, operUserId, operTime);
    }

    @Override
    public void mdf(MdfResourceDMO dmo, String operUserId, Date operTime) throws Exception {
        Resource po = MdfResourceDMO.toResource(dmo);
        this.rep.update(po, operUserId, operTime);
    }

    @Override
    public void del(String id, String operUserId, Date operTime) throws Exception {
        // 删除前记录
        Resource po = this.rep.selectById(id);

        if (po == null){
            return;
        }

        DelRecordDmo dmo = new DelRecordDmo(Resource.__TABLENAME, id, jackson.toJson(po));
        this.delRecordDomain.record(dmo, operUserId, operTime);

        this.rep.del(id);

        // 删除 roleResource
    }
}
