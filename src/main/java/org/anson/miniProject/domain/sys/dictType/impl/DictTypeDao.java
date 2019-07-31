package org.anson.miniProject.domain.sys.dictType.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.anson.miniProject.domain.base.BaseDao;
import org.anson.miniProject.domain.internal.deletedRecord.DelHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
@Transactional(rollbackFor = Exception.class)
class DictTypeDao extends BaseDao<DictType, DictTypeMapper> {
    public String insert(DictType dictType){
        dictType.setId(dictType.getNo());
        dictType.setCreateTime(operTime);
        dictType.setUpdateTime(operTime);

        this.mapper.insert(dictType);

        return dictType.getId();
    }

    public void updateById(DictType dictType){
        dictType.setNo(null); // 编码不可修改
        dictType.setCreateTime(null);
        dictType.setUpdateTime(operTime);

        this.mapper.updateById(dictType);
    }

    public void deleteById(String id) throws Exception{
        DictType dictType = this.mapper.selectById(id);

        if (dictType == null){
            return;
        }

        this.mapper.deleteById(id);
        this.delHelper.recordDelData(dictType);
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Boolean isExistsByNo(String no){
        QueryWrapper<DictType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DictType.NO, no);

        Integer count = this.mapper.selectCount(queryWrapper);

        return count >= 1 ? true : false;
    }
    // 私有方法(没有事务)

    // 注入
    @Override
    @Autowired
    protected void setMapper(DictTypeMapper mapper) {
        this.mapper = mapper;
    }
    @Autowired
    private DelHelper delHelper;
    private Date operTime = new Date();
}
