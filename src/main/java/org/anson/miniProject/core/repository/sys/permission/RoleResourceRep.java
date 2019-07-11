package org.anson.miniProject.core.repository.sys.permission;

import org.anson.miniProject.core.mapper.sys.permission.RoleResourceMapper;
import org.anson.miniProject.core.model.po.sys.permission.RoleResource;
import org.anson.miniProject.core.repository.BaseRep;
import org.anson.miniProject.tool.helper.IdHelper;
import org.anson.miniProject.tool.helper.InputParamHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

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

    public void del(String id){
        this.mapper.deleteById(id);
    }

    public void del(Collection<? extends Serializable> idList){
        this.mapper.deleteBatchIds(idList);
    }

    // 查询 (不需要事务)

    // 注入
    @Autowired
    public void setMapper(RoleResourceMapper mapper){
        this.mapper = mapper;
    }
}
