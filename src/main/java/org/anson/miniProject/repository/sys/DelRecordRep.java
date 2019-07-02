package org.anson.miniProject.repository.sys;

import cn.hutool.core.util.StrUtil;
import org.anson.miniProject.mapper.sys.DelRecordMapper;
import org.anson.miniProject.model.entity.sys.DelRecord;
import org.anson.miniProject.tool.helper.IdHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
@Transactional(rollbackFor = Exception.class)
public class DelRecordRep {
    @Autowired
    private DelRecordMapper mapper;

    public String insert(DelRecord entity, String operUserId, Date operTime){
        // 检查表名
        if(StrUtil.isEmpty(entity.getTableName())){
            throw new RuntimeException("请输入表名");
        }

        // 检查主键
        if(StrUtil.isEmpty(entity.getPk())){
            throw new RuntimeException("请输入主键");
        }

        // 检查记录
        if(StrUtil.isEmpty(entity.getRecord())){
            throw new RuntimeException("请输入记录");
        }

        entity.setId(IdHelper.nextSnowflakeId());
        entity.setDeletedUserId(operUserId);
        entity.setDeletedTime(operTime);
        entity.setCreateUserId(operUserId);
        entity.setCreateTime(operTime);
        entity.setLastUpdateTime(operTime);

        this.mapper.insert(entity);

        return entity.getId();
    }
}
