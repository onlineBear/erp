package org.anson.miniProject.core.repository.sys.permission;

import org.anson.miniProject.core.mapper.sys.permission.UserRoleMapper;
import org.anson.miniProject.core.model.po.sys.permission.UserRole;
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

    public void del(String id){
        this.mapper.deleteById(id);
    }

    public void del(Collection<? extends Serializable> idList){
        this.mapper.deleteBatchIds(idList);
    }

    // 查询 (不需要事务)

    // 注入
    @Autowired
    public void setMapper(UserRoleMapper mapper){
        this.mapper = mapper;
    }
}
