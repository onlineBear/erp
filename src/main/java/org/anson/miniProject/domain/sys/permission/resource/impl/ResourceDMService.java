package org.anson.miniProject.domain.sys.permission.resource.impl;

import org.anson.miniProject.domain.internal.permission.roleResource.IRoleResourceHelper;
import org.anson.miniProject.domain.sys.permission.resource.IResourceDMService;
import org.anson.miniProject.domain.sys.permission.resource.cmd.AddResourceCMD;
import org.anson.miniProject.domain.sys.permission.resource.cmd.UpdResourceCMD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(rollbackFor = Exception.class)
class ResourceDMService implements IResourceDMService {
    @Override
    public String addResource(AddResourceCMD cmd) throws Exception {
        // 检查 cmd

        Resource resource = AddResourceCMDTranslator.toResource(cmd);

        String id = this.dao.insert(resource);
        return id;
    }

    @Override
    public void updateResource(UpdResourceCMD cmd) throws Exception {
        // 检查 cmd

        Resource resource = UpdResourceCMDTranslator.toResource(cmd);

        this.dao.updateById(resource);
    }

    @Override
    public void deleteResource(String id) throws Exception {
        // 删除资源
        this.dao.deleteById(id);
        // 删除角色资源
        this.roleResourceHelper.deleteByResource(id);
    }

    // 注入
    @Autowired
    private ResourceDao dao;
    @Autowired
    private IRoleResourceHelper roleResourceHelper;
}
