package org.anson.miniProject.domain.internal.roleResource.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.anson.miniProject.domain.base.BaseDao;
import org.anson.miniProject.domain.internal.deletedRecord.DelHelper;
import org.anson.miniProject.tool.helper.IdHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Component
@Transactional(rollbackFor = Exception.class)
public class RoleResourceDao extends BaseDao<RoleResource, RoleResourceMapper> {

    public String insert(RoleResource roleResource){
        roleResource.setId(IdHelper.nextSnowflakeId());
        roleResource.setCreateUserId(operUserId);
        roleResource.setCreateTime(operTime);
        roleResource.setLastUpdateTime(operTime);

        this.mapper.insert(roleResource);

        return roleResource.getId();
    }

    public void updateById(RoleResource roleResource){
        roleResource.setCreateUserId(null);
        roleResource.setCreateTime(null);
        roleResource.setLastUpdateTime(operTime);

        this.mapper.updateById(roleResource);
    }

    public void deleteByResource(String resourceId) throws Exception {
        if (StrUtil.isEmpty(resourceId)){
            return;
        }

        QueryWrapper<RoleResource> qw = new QueryWrapper<>();
        qw.eq(RoleResource.RESOURCEID, resourceId);

        List<RoleResource> list = this.mapper.selectList(qw);

        if (CollUtil.isEmpty(list)){
            return;
        }

        this.mapper.delete(qw);

        for (RoleResource roleResource : list){
            this.delHelper.recordDelData(roleResource);
        }
    }

    public void deleteByRole(String roleId) throws Exception {
        QueryWrapper<RoleResource> qw = new QueryWrapper<>();
        qw.eq(RoleResource.ROLEID, roleId);

        List<RoleResource> list = this.mapper.selectList(qw);

        if (CollUtil.isEmpty(list)){
            return;
        }

        this.mapper.delete(qw);

        for (RoleResource roleResource : list){
            this.delHelper.recordDelData(roleResource);
        }
    }

    public void delete(String roleId, String resourceId) throws Exception {
        QueryWrapper<RoleResource> qw = new QueryWrapper<>();
        qw.eq(RoleResource.ROLEID, roleId)
                .eq(RoleResource.RESOURCEID, resourceId);

        RoleResource roleResource = this.mapper.selectOne(qw);

        if (roleResource == null){
            return;
        }

        this.mapper.delete(qw);
        this.delHelper.recordDelData(roleResource);
    }

    // 查询
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<RoleResource> listByRole(String roleId){
        QueryWrapper<RoleResource> qw = new QueryWrapper<>();
        qw.eq(RoleResource.ROLEID, roleId);

        return this.mapper.selectList(qw);
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<RoleResource> listByResource(String resourceId){
        QueryWrapper<RoleResource> qw = new QueryWrapper<>();
        qw.eq(RoleResource.RESOURCEID, resourceId);

        return this.mapper.selectList(qw);
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public RoleResource selectOne(String roleId, String resourceId){
        QueryWrapper<RoleResource> qw = new QueryWrapper<>();
        qw.eq(RoleResource.ROLEID, roleId)
            .eq(RoleResource.RESOURCEID, resourceId);

        return this.mapper.selectOne(qw);
    }

    // 注入
    @Autowired
    @Override
    protected void setMapper(RoleResourceMapper mapper) {
        this.mapper = mapper;
    }
    @Autowired
    private DelHelper delHelper;
    private String operUserId = "operUserId";
    private Date operTime = new Date();
}
