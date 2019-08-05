package org.anson.miniProject.domain.internal.deletedRecord;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
interface DeletedRecordMapper extends BaseMapper<DeletedRecord> {
}
