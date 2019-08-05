package org.anson.miniProject.domain.account.user.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
interface UserMapper extends BaseMapper<User> {
}
