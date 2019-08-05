package org.anson.miniProject.domain.internal.loginLog.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
interface LoginLogMapper extends BaseMapper<LoginLog> {
}
