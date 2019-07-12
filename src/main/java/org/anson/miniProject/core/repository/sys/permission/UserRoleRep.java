package org.anson.miniProject.core.repository.sys.permission;

import cn.hutool.core.collection.IterUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.anson.miniProject.core.mapper.sys.permission.UserRoleMapper;
import org.anson.miniProject.core.model.po.sys.permission.UserRole;
import org.anson.miniProject.core.repository.BaseRep;
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
public class UserRoleRep extends BaseRep<UserRole, UserRoleMapper> {

    // 更新等 (需要事务)
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

    public void del(String id, String operUserId, Date operTime) throws JsonProcessingException {
        UserRole po = this.mapper.selectById(id);

        if (po == null){
            return;
        }

        this.delHelper.recordDelData(po, operUserId, operTime);

        this.mapper.deleteById(id);
    }

    public void delByUser(String userId, String operUserId, Date operTime) throws JsonProcessingException{
        QueryWrapper<UserRole> qw = new QueryWrapper<>();
        qw.eq(UserRole.USERID, userId);

        List<UserRole> poList = this.mapper.selectList(qw);

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

    public void delByRole(String roleId, String operUserId, Date operTime) throws JsonProcessingException{
        QueryWrapper<UserRole> qw = new QueryWrapper<>();
        qw.eq(UserRole.ROLEID, roleId);

        List<UserRole> poList = this.mapper.selectList(qw);

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

    // 注入
    @Autowired
    public void setMapper(UserRoleMapper mapper){
        this.mapper = mapper;
    }
    @Autowired
    private LogicDelHelper delHelper;
}
