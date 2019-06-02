package org.zmqy.erp.mapper.mis.biz.sys.security;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import org.zmqy.erp.model.mis.entity.sys.security.UserResource;

import java.util.List;
import java.util.Map;

@Repository
public interface UserResourceMapper extends BaseMapper<UserResource> {
    public List<Map> geRecentUserResource();
    public void batchInsert(List<UserResource> userResourceList);
}
