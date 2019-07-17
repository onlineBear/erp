package org.anson.miniProject.core.repository.sys.permission.impl;

import cn.hutool.core.collection.IterUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.anson.miniProject.core.mapper.sys.permission.UserRoleMapper;
import org.anson.miniProject.core.model.po.sys.permission.UserRole;
import org.anson.miniProject.core.repository.BaseRep;
import org.anson.miniProject.core.repository.sys.permission.IUserRoleRep;
import org.anson.miniProject.tool.helper.IdHelper;
import org.anson.miniProject.tool.helper.InputParamHelper;
import org.anson.miniProject.tool.helper.LogicDelHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Transactional(rollbackFor = Exception.class)
public class UserRoleRep extends BaseRep<UserRole, UserRoleMapper>
                         implements IUserRoleRep {

    // 更新等 (需要事务)
    @Override
    public String insert(UserRole po, String operUserId, Date operTime){
        // 必填检查
        Object[] valArray = {po.getUserId(), po.getRoleId()};
        String[] errArray = {"请输入用户id", "请输入角色id"};
        InputParamHelper.required(valArray, errArray);

        po.setId(IdHelper.nextSnowflakeId());
        po.setCreateUserId(operUserId);
        po.setCreateTime(operTime);
        po.setLastUpdateTime(operTime);

        this.mapper.insert(po);

        return po.getId();
    }

    @Override
    public void del(String userId, String roleId, String operUserId, Date operTime) throws JsonProcessingException {

        UserRole po = this.get(userId, roleId);

        if (po == null){
            return;
        }

        this.delHelper.recordDelData(po, operUserId, operTime);

        this.mapper.deleteById(po.getId());
    }

    @Override
    public void delByUser(String userId, String operUserId, Date operTime) throws JsonProcessingException{
        QueryWrapper<UserRole> qw = new QueryWrapper<>();
        qw.eq(UserRole.USERID, userId);

        List<UserRole> poList = this.mapper.selectList(qw);

        this.del(poList, operUserId, operTime);
    }

    @Override
    public void delByUser(String userId, List<String> roleIdList, String operUserId, Date operTime) throws JsonProcessingException{
        if (IterUtil.isEmpty(roleIdList)){
            return;
        }

        QueryWrapper<UserRole> qw = new QueryWrapper<>();
        qw.eq(UserRole.USERID, userId)
            .in(UserRole.ROLEID, roleIdList);

        List<UserRole> poList = this.mapper.selectList(qw);

        this.del(poList, operUserId, operTime);
    }

    @Override
    public void delByRole(String roleId, String operUserId, Date operTime) throws JsonProcessingException{
        QueryWrapper<UserRole> qw = new QueryWrapper<>();
        qw.eq(UserRole.ROLEID, roleId);

        List<UserRole> poList = this.mapper.selectList(qw);

        this.del(poList, operUserId, operTime);
    }

    @Override
    public void delByRole(String roleId, List<String> userIdList, String operUserId, Date operTime) throws JsonProcessingException{
        if (IterUtil.isEmpty(userIdList)){
            return;
        }

        QueryWrapper<UserRole> qw = new QueryWrapper<>();
        qw.eq(UserRole.ROLEID, roleId)
                .in(UserRole.USERID, userIdList);

        List<UserRole> poList = this.mapper.selectList(qw);

        this.del(poList, operUserId, operTime);
    }

    // 内部方法
    public void del(List<UserRole> poList, String operUserId, Date operTime) throws JsonProcessingException {
        if (IterUtil.isEmpty(poList)) {
            return;
        }

        if (IterUtil.isEmpty(poList)){
            return;
        }

        List<String> idList = new ArrayList<>();

        for (UserRole po : poList){
            this.delHelper.recordDelData(po, operUserId, operTime);
            idList.add(po.getId());
        }

        this.mapper.deleteBatchIds(idList);
    }

    // 查询 (不需要事务)
    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<String> selUserIdListByRole(String roleId) {
        QueryWrapper<UserRole> qw = new QueryWrapper<>();
        qw.eq(UserRole.ROLEID, roleId)
                .select(UserRole.USERID, UserRole.ID);

        List<UserRole> poList = this.mapper.selectList(qw);

        if (IterUtil.isEmpty(poList)){
            return null;
        }

        List<String> userIdList = new ArrayList<>();

        for (UserRole po : poList){
            userIdList.add(po.getUserId());
        }

        return userIdList;
    }

    // private
    private UserRole get(String userId, String roleId){
        QueryWrapper<UserRole> qw = new QueryWrapper<>();
        qw.eq(UserRole.USERID, userId)
                .eq(UserRole.ROLEID, roleId);

        return this.mapper.selectOne(qw);
    }

    // 注入
    @Autowired
    public void setMapper(UserRoleMapper mapper){
        this.mapper = mapper;
    }
    @Autowired
    private LogicDelHelper delHelper;
}
