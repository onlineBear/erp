package org.anson.miniProject.domain.internal.permission.userRole.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
interface UserRoleMapper extends BaseMapper<UserRole> {
}
