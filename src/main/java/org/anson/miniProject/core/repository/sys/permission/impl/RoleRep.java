package org.anson.miniProject.core.repository.sys.permission.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.anson.miniProject.core.mapper.sys.permission.RoleMapper;
import org.anson.miniProject.core.model.po.sys.permission.Role;
import org.anson.miniProject.core.model.po.sys.permission.RoleResource;
import org.anson.miniProject.core.repository.BaseRep;
import org.anson.miniProject.core.repository.sys.permission.IRoleResourceRep;
import org.anson.miniProject.core.repository.sys.permission.IUserRoleRep;
import org.anson.miniProject.tool.helper.InputParamHelper;
import org.anson.miniProject.tool.helper.LogicDelHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Component
@Transactional(rollbackFor = Exception.class)
public class RoleRep extends BaseRep<Role, RoleMapper> {

    // 更新等 (需要事务)
    public String insert(Role po, String operUserId, Date operTime){
        // 必填检查
        Object[] valArray = {po.getNo(), po.getName()};
        String[] errArray = {"请输入角色编码", "请输入角色名称"};
        InputParamHelper.required(valArray, errArray);

        QueryWrapper<Role> qw = null;
        // 检查 parentRoleId
        if (StrUtil.isNotBlank(po.getParentRoleId())){
            qw = new QueryWrapper();
            qw.eq(Role.ID, po.getParentRoleId());
            if (this.mapper.selectCount(qw) < 1){
                throw new RuntimeException(String.format("没有这个父级角色, 角色id : {}", po.getParentRoleId()));
            }
        }

        // 检查 no
        qw = new QueryWrapper();
        qw.eq(Role.NO, po.getNo());
        if (this.mapper.selectCount(qw) >= 1){
            throw new RuntimeException(String.format("角色编码重复了, 请换一个角色编码, 角色编码 : {}", po.getNo()));
        }

        po.setId(po.getNo());
        po.setCreateUserId(operUserId);
        po.setCreateTime(operTime);
        po.setLastUpdateTime(operTime);

        this.mapper.insert(po);

        return po.getId();
    }

    public void update(Role po, Date operTime){
        // 必填检查
        Object[] valArray = {po.getId()};
        String[] errArray = {"请输入角色id"};
        InputParamHelper.required(valArray, errArray);

        Role oldPo = this.mapper.selectById(po.getId());

        if (oldPo == null){
            return;
        }

        QueryWrapper<Role> qw = null;

        // 检查 parentRoleId
        if (StrUtil.isNotBlank(po.getParentRoleId()) && !po.getParentRoleId().equals(oldPo.getParentRoleId())){
            qw = new QueryWrapper<>();
            qw.eq(Role.PARENTROLEID, po.getParentRoleId());
            if (this.mapper.selectCount(qw) < 1){
                throw new RuntimeException(String.format("没有这个父级角色, 角色id : {}", po.getParentRoleId()));
            }
        }

        // 检查编码
        if (StrUtil.isNotBlank(po.getNo()) && !po.getNo().equals(oldPo.getNo())){
            qw = new QueryWrapper();
            qw.eq(Role.NO, po.getNo())
              .le(Role.ID, po.getId());
            if (this.mapper.selectCount(qw) >= 1){
                throw new RuntimeException(String.format("角色编码重复了, 请换一个角色编码, 角色编码 : {}", po.getNo()));
            }
        }

        po.setCreateUserId(null);
        po.setCreateTime(null);
        po.setLastUpdateTime(operTime);

        this.mapper.updateById(po);
    }

    public void del(String roleId, String operUserId, Date operTime) throws JsonProcessingException {
        Role po = this.mapper.selectById(roleId);

        if (po == null){
            return;
        }

        this.delHelper.recordDelData(po, operUserId, operTime);
        this.mapper.deleteById(roleId);
    }

    // 查询 (不需要事务)

    // 注入
    @Autowired
    public void setMapper(RoleMapper mapper){
        this.mapper = mapper;
    }
    @Autowired
    private LogicDelHelper delHelper;
}