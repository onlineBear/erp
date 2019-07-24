package org.anson.miniProject.domain.sys.dictType.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.anson.miniProject.domain.base.BaseDao;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
@Transactional(rollbackFor = Exception.class)
class DictTypeDao extends BaseDao<DictType, DictTypeMapper> {

    private String operUserId = "createUserId";
    private Date operTime = new Date();

    public String insert(DictType po){
        po.setId(po.getNo());
        po.setCreateUserId(operUserId);
        po.setCreateTime(operTime);
        po.setLastUpdateTime(operTime);

        this.mapper.insert(po);

        return po.getId();
    }

    public void updateById(DictType po){
        po.setNo(null); // 编码不可修改
        po.setCreateUserId(null);
        po.setCreateTime(null);
        po.setLastUpdateTime(operTime);

        this.mapper.updateById(po);
    }

    public void deleteById(String id){
        this.mapper.deleteById(id);
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
    public void setMapper(DictTypeMapper mapper) {
        this.mapper = mapper;
    }
}
