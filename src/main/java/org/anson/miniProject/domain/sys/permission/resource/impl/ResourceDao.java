package org.anson.miniProject.domain.sys.permission.resource.impl;

import org.anson.miniProject.domain.base.BaseDao;
import org.anson.miniProject.domain.internal.deletedRecord.DelHelper;
import org.anson.miniProject.tool.helper.IdHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
@Transactional(rollbackFor = Exception.class)
class ResourceDao extends BaseDao<Resource, ResourceMapper> {

    public String insert(Resource resource){
        resource.setId(IdHelper.nextSnowflakeId());
        resource.setCreateTime(operDate);
        resource.setUpdateTime(operDate);

        this.mapper.insert(resource);

        return resource.getId();
    }

    public void updateById(Resource resource){
        resource.setCreateTime(null);
        resource.setUpdateTime(operDate);

        this.mapper.updateById(resource);
    }

    public void deleteById(String id) throws Exception {
        Resource resource = this.mapper.selectById(id);

        if (resource == null){
            return;
        }

        this.delHelper.recordDelData(resource);
        this.mapper.deleteById(id);
    }

    // 注入
    @Autowired
    @Override
    protected void setMapper(ResourceMapper mapper) {
        this.mapper = mapper;
    }
    @Autowired
    private DelHelper delHelper;
    private String operUserId = "operUserId";
    private Date operDate = new Date();
}
