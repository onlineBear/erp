package org.anson.miniProject.repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.anson.miniProject.model.entity.BaseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Transactional(rollbackFor = Exception.class)
public abstract class BaseRep<E extends BaseEntity, M extends BaseMapper<E>> {
    protected M mapper;

    // 查询
    @Transactional(readOnly = true)
    public E selectById(Serializable id) {
        return this.mapper.selectById(id);
    }

    @Transactional(readOnly = true)
    public List<E> selectBatchIds(Collection<? extends Serializable> idList) {
        return this.mapper.selectBatchIds(idList);
    }

    @Transactional(readOnly = true)
    public List<E> selectByMap(Map<String, Object> columnMap) {
        return this.mapper.selectByMap(columnMap);
    }

    @Transactional(readOnly = true)
    public E selectOne(Wrapper<E> queryWrapper) {
        return this.mapper.selectOne(queryWrapper);
    }

    @Transactional(readOnly = true)
    public Integer selectCount(Wrapper<E> queryWrapper) {
        return this.mapper.selectCount(queryWrapper);
    }

    @Transactional(readOnly = true)
    public List<E> selectList(Wrapper<E> queryWrapper) {
        return this.mapper.selectList(queryWrapper);
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> selectMaps(Wrapper<E> queryWrapper) {
        return this.mapper.selectMaps(queryWrapper);
    }

    @Transactional(readOnly = true)
    public List<Object> selectObjs(Wrapper<E> queryWrapper) {
        return this.mapper.selectObjs(queryWrapper);
    }
}
