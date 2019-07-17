package org.anson.miniProject.core.repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.anson.miniProject.core.model.po.BasePO;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Transactional(rollbackFor = Exception.class)
public abstract class BaseRep<P extends BasePO, M extends BaseMapper<P>> implements IBaseRep<P, M> {
    protected M mapper;

    // 查询
    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public P selectById(Serializable id) {
        return this.mapper.selectById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<P> selectBatchIds(Collection<? extends Serializable> idList) {
        return this.mapper.selectBatchIds(idList);
    }

    /*@Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<P> selectByMap(Map<String, Object> columnMap) {
        return this.mapper.selectByMap(columnMap);
    }
     */

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public P selectOne(Wrapper<P> queryWrapper) {
        return this.mapper.selectOne(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Integer selectCount(Wrapper<P> queryWrapper) {
        return this.mapper.selectCount(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<P> selectList(Wrapper<P> queryWrapper) {
        return this.mapper.selectList(queryWrapper);
    }

    /*
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<Map<String, Object>> selectMaps(Wrapper<P> queryWrapper) {
        return this.mapper.selectMaps(queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<Object> selectObjs(Wrapper<P> queryWrapper) {
        return this.mapper.selectObjs(queryWrapper);
    }
    */
    public abstract void setMapper(M mapper);
}
