package org.anson.miniProject.domain.sys.permission.role.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
interface RoleMapper extends BaseMapper<Role> {
}
