package org.anson.miniProject.core.repository.sys.permission;

import cn.hutool.core.collection.IterUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.anson.miniProject.core.mapper.sys.permission.RoleResourceMapper;
import org.anson.miniProject.core.model.po.sys.permission.RoleResource;
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
public class RoleResourceRep extends BaseRep<RoleResource, RoleResourceMapper> {

    // 更新等 (需要事务)
    public String insert(RoleResource po, String operUserId, Date operTime){
        // 必填检查
        Object[] valArray = {po.getRoleId(), po.getResourceId()};
        String[] errArray = {"请输入角色id", "请输入资源id"};
        InputParamHelper.required(valArray, errArray);

        po.setId(IdHelper.nextSnowflakeId());
        po.setCreateUserId(operUserId);
        po.setCreateTime(operTime);
        po.setLastUpdateTime(operTime);

        this.mapper.insert(po);

        return po.getId();
    }

    public void del(String id, String operUserId, Date operTime) throws JsonProcessingException {
        RoleResource po = this.mapper.selectById(id);

        if (po == null){
            return;
        }

        this.delHelper.recordDelData(po, operUserId, operTime);
        this.mapper.deleteById(id);
    }

    public void delByRole(String roleId, String operUserId, Date operTime) throws JsonProcessingException {
        QueryWrapper<RoleResource> qw = new QueryWrapper<>();
        qw.eq(RoleResource.ROLEID, roleId);

        List<RoleResource> poList = this.mapper.selectList(qw);

        if (IterUtil.isEmpty(poList)){
            return;
        }

        List<String> idList = new ArrayList<>();

        for (RoleResource po : poList){
            this.delHelper.recordDelData(po, operUserId, operTime);
            idList.add(po.getId());
        }

        this.mapper.deleteBatchIds(idList);
    }

    public void delByResource(String resourceId, String operUserId, Date operTime) throws JsonProcessingException {
        QueryWrapper<RoleResource> qw = new QueryWrapper<>();
        qw.eq(RoleResource.RESOURCEID, resourceId);

        List<RoleResource> poList = this.mapper.selectList(qw);

        if (IterUtil.isEmpty(poList)){
            return;
        }

        List<String> idList = new ArrayList<>();

        for (RoleResource po : poList){
            this.delHelper.recordDelData(po, operUserId, operTime);
            idList.add(po.getId());
        }

        this.mapper.deleteBatchIds(idList);
    }

    // 查询 (不需要事务)

    // 注入
    @Autowired
    public void setMapper(RoleResourceMapper mapper){
        this.mapper = mapper;
    }
    @Autowired
    private LogicDelHelper delHelper;
}
