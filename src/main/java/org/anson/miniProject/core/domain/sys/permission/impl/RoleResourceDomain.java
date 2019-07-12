package org.anson.miniProject.core.domain.sys.permission.impl;

import cn.hutool.core.collection.IterUtil;
import org.anson.miniProject.core.domain.sys.permission.IRoleResourceDomain;
import org.anson.miniProject.core.model.po.sys.permission.RoleResource;
import org.anson.miniProject.core.repository.sys.permission.impl.RoleResourceRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Component
@Transactional(rollbackFor = Exception.class)
public class RoleResourceDomain implements IRoleResourceDomain {
    @Autowired
    private RoleResourceRep rep;

    @Override
    public String add(String roleId, String resourceId, String operUserId, Date operTime) throws Exception {
        RoleResource po = RoleResource.builder()
                            .roleId(roleId)
                            .resourceId(resourceId)
                            .build();
        String id = this.rep.insert(po, operUserId, operTime);
        return id;
    }

    @Override
    public void addByRole(String roleId, List<String> resourceIdList, String operUserId, Date operTime) throws Exception {
        if (IterUtil.isEmpty(resourceIdList)){
            return;
        }

        for (String resourceId : resourceIdList){
            this.add(roleId, resourceId, operUserId, operTime);
        }
    }

    @Override
    public void addByResource(String resourceId, List<String> roleIdList, String operUserId, Date operTime) throws Exception {
        if (IterUtil.isEmpty(roleIdList)){
            return;
        }

        for (String roleId : roleIdList){
            this.add(roleId, resourceId, operUserId, operTime);
        }
    }

    @Override
    public void delByResource(String resId, String operUserId, Date operTime) throws Exception {
        this.rep.delByResource(resId, operUserId, operTime);
    }

    @Override
    public void delByRole(String roleId, String operUserId, Date operTime) throws Exception {
        this.rep.delByRole(roleId, operUserId, operTime);
    }
}
