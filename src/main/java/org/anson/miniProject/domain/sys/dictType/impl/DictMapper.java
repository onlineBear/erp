package org.anson.miniProject.domain.sys.dictType.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
interface DictMapper extends BaseMapper<Dict> {
}
