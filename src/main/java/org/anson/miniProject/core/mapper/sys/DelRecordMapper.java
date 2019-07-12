package org.anson.miniProject.core.mapper.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.anson.miniProject.core.model.po.sys.DeletedRecord;
import org.springframework.stereotype.Repository;

@Repository
public interface DelRecordMapper extends BaseMapper<DeletedRecord> {
}
