package org.anson.miniProject.domain.sys.permission.role.impl;

import org.anson.miniProject.domain.base.BaseDao;
import org.anson.miniProject.domain.internal.deletedRecord.DelHelper;
import org.anson.miniProject.tool.helper.IdHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
@Transactional(rollbackFor = Exception.class)
class RoleDao extends BaseDao<Role, RoleMapper> {

    public String insert(Role role){
        role.setId(IdHelper.nextSnowflakeId());
        role.setCreateUserId(operUserId);
        role.setLastUpdateTime(operTime);
        role.setLastUpdateTime(operTime);

        this.mapper.insert(role);

        return role.getId();
    }

    public void updateById(Role role){
        role.setCreateUserId(null);
        role.setCreateTime(null);
        role.setLastUpdateTime(operTime);

        this.mapper.updateById(role);
    }

    public void deleteById(String id) throws Exception {
        Role role = this.mapper.selectById(id);

        if (role == null){
            return;
        }

        this.mapper.deleteById(id);
        this.delHelper.recordDelData(role);
    }

    // 注入
    @Autowired
    @Override
    protected void setMapper(RoleMapper mapper) {
        this.mapper = mapper;
    }
    @Autowired
    private DelHelper delHelper;
    private String operUserId = "operUserId";
    private Date operTime = new Date();
}
