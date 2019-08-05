package org.anson.miniProject.domain.sys.menu.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
interface MenuMapper extends BaseMapper<Menu> {
}
