package org.anson.miniProject.repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface BaseRepository<T> {
    T selectById(Serializable id);
    List<T> selectBatchIds(Collection<? extends Serializable> idList);
    List<T> selectByMap(Map<String, Object> columnMap);
    T selectOne(Wrapper<T> queryWrapper);
    Integer selectCount(Wrapper<T> queryWrapper);
    List<T> selectList(Wrapper<T> queryWrapper);
    List<Map<String, Object>> selectMaps(Wrapper<T> queryWrapper);
    List<Object> selectObjs(Wrapper<T> queryWrapper);
}
