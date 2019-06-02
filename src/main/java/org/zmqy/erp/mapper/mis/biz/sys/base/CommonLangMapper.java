package org.zmqy.erp.mapper.mis.biz.sys.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.zmqy.erp.model.mis.entity.sys.base.CommonLang;

@Repository
public interface CommonLangMapper extends BaseMapper<CommonLang> {
}