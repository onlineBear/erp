package org.anson.miniProject.domain.internal.permission.userRole.impl;

import cn.hutool.core.collection.CollUtil;
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
class UserRoleDao extends BaseDao<UserRole, UserRoleMapper> {
    public String insert(UserRole userRole){
        userRole.setId(IdHelper.nextSnowflakeId());
        userRole.setCreateTime(operTime);
        userRole.setUpdateTime(operTime);

        this.mapper.insert(userRole);

        return userRole.getId();
    }

    public void deleteByUser(String userId) throws Exception {
        QueryWrapper<UserRole> qw = new QueryWrapper<>();
        qw.eq(UserRole.USERID, userId);

        List<UserRole> list = this.mapper.selectList(qw);

        if (CollUtil.isEmpty(list)){
            return;
        }

        this.mapper.delete(qw);

        for (UserRole userRole : list){
            this.delHelper.recordDelData(userRole);
        }
    }

    public void deleteByUser(String userId, List<String> roleIdList) throws Exception {
        QueryWrapper<UserRole> qw = new QueryWrapper<>();
        qw.eq(UserRole.USERID, userId)
            .in(UserRole.ROLEID, roleIdList);

        List<UserRole> list = this.mapper.selectList(qw);

        if (CollUtil.isEmpty(list)){
            return;
        }

        this.mapper.delete(qw);

        for (UserRole userRole : list){
            this.delHelper.recordDelData(userRole);
        }
    }

    public void deleteByRole(String roleId) throws Exception {
        QueryWrapper<UserRole> qw = new QueryWrapper<>();
        qw.eq(UserRole.ROLEID, roleId);

        List<UserRole> list = this.mapper.selectList(qw);

        if (CollUtil.isEmpty(list)){
            return;
        }

        this.mapper.delete(qw);

        for (UserRole userRole : list){
            this.delHelper.recordDelData(userRole);
        }
    }

    public void deleteByRole(String roleId, List<String> userIdList) throws Exception {
        QueryWrapper<UserRole> qw = new QueryWrapper<>();
        qw.eq(UserRole.ROLEID, roleId)
            .in(UserRole.USERID, userIdList);

        List<UserRole> list = this.mapper.selectList(qw);

        if (CollUtil.isEmpty(list)){
            return;
        }

        this.mapper.delete(qw);

        for (UserRole userRole : list){
            this.delHelper.recordDelData(userRole);
        }
    }

    // 注入
    @Autowired
    @Override
    protected void setMapper(UserRoleMapper mapper) {
        this.mapper = mapper;
    }
    @Autowired
    private DelHelper delHelper;
    private String operUserId = "operUserId";
    private Date operTime = new Date();
}
