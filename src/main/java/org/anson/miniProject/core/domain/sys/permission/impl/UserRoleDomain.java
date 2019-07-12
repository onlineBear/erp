package org.anson.miniProject.core.domain.sys.permission.impl;

import cn.hutool.core.collection.IterUtil;
import org.anson.miniProject.core.domain.sys.permission.IUserRoleDomain;
import org.anson.miniProject.core.model.po.sys.permission.UserRole;
import org.anson.miniProject.core.repository.sys.permission.IUserRoleRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Component
@Transactional(rollbackFor = Exception.class)
public class UserRoleDomain implements IUserRoleDomain {
    @Autowired
    private IUserRoleRep rep;

    @Override
    public void addByRole(String roleId, List<String> userIdList, String operUserId, Date operTime) throws Exception {
        if (IterUtil.isEmpty(userIdList)){
            return;
        }

        for (String userId : userIdList){
            this.add(roleId, userId, operUserId, operTime);
        }
    }

    @Override
    public void addByUser(String userId, List<String> roleIdList, String operUserId, Date operTime) throws Exception {
        if (IterUtil.isEmpty(roleIdList)){
            return;
        }

        for (String roleId : roleIdList){
            this.add(roleId, userId, operUserId, operTime);
        }
    }

    @Override
    public void delByUser(String userId, String operUserId, Date operTime) throws Exception {
        this.rep.delByUser(userId, operUserId, operTime);
    }

    @Override
    public void delByRole(String roleId, String operUserId, Date operTime) throws Exception {
        this.rep.delByRole(roleId, operUserId, operTime);
    }

    @Override
    public void delByUser(String userId, List<String> roleIdList, String operUserId, Date operTime) throws Exception {
        this.rep.delByUser(userId, roleIdList, operUserId, operTime);
    }

    @Override
    public void delByRole(String roleId, List<String> userIdList, String operUserId, Date operTime) throws Exception {
        this.rep.delByRole(roleId, userIdList, operUserId, operTime);
    }

    // 查询(不需要事务)
    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<String> getUserIdListByRole(String roleId) {
        return this.rep.getUserIdListByRole(roleId);
    }

    // 内部方法
    public String add(String roleId, String userId, String operUserId, Date operTime){
        UserRole po = UserRole.builder()
                        .userId(userId)
                        .roleId(roleId)
                        .build();
        return this.rep.insert(po, operUserId, operTime);
    }
}
