package org.zmqy.erp.mapper.mis.biz.sys.columnInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.zmqy.erp.model.mis.entity.sys.columnInfo.DtlColumn;

import java.util.List;
import java.util.Map;

@Repository
public interface DtlColumnMapper extends BaseMapper<DtlColumn> {
}