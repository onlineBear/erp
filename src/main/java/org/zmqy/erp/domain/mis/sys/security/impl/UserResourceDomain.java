package org.zmqy.erp.domain.mis.sys.security.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.zmqy.erp.domain.mis.sys.security.IUserResourceDomain;
import org.zmqy.erp.mapper.mis.biz.sys.security.UserResourceMapper;
import org.zmqy.erp.model.mis.entity.sys.security.UserResource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class UserResourceDomain implements IUserResourceDomain {
    @Autowired
    private UserResourceMapper mapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void reBuild(String operUserId, Date operTime) throws Exception{
        // 删除全部的 tUserResource
        this.mapper.delete(null);

        // 查出最新的
        List<Map> list = this.mapper.geRecentUserResource();

        // 插入
        List<UserResource> userResourceList = new ArrayList<>();

        if(list != null && list.size() > 0){
            for(Map map : list){
                UserResource userResource = new UserResource();
                userResource.setUserId((String)map.get("userId"));
                userResource.setResId((String)map.get("resId"));

                userResource.setCreateUserId(operUserId);
                userResource.setCreateTime(operTime);
                userResource.setLastUpdateTime(operTime);

                userResourceList.add(userResource);
            }
        }

        if(userResourceList != null && userResourceList.size() > 0){
            this.mapper.batchInsert(userResourceList);
        }
    }

}
