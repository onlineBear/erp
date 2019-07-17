package org.anson.miniProject.core.repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.anson.miniProject.core.model.po.BasePO;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public interface IBaseRep<P extends BasePO, M extends BaseMapper<P>> {
    P selectById(Serializable id);

    List<P> selectBatchIds(Collection<? extends Serializable> idList);

    P selectOne(Wrapper<P> queryWrapper);

    Integer selectCount(Wrapper<P> queryWrapper);

    List<P> selectList(Wrapper<P> queryWrapper);
}
