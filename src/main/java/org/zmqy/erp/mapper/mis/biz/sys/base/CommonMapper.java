package org.zmqy.erp.mapper.mis.biz.sys.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.zmqy.erp.model.mis.entity.sys.base.Common;

@Repository
public interface CommonMapper extends BaseMapper<Common> {
}