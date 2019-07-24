package org.anson.miniProject.domain.base;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public abstract class BaseDao<P extends BasePO, M extends BaseMapper<P>>{
    protected M mapper;

    // 查询
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public P selectById(String id) {
        return this.mapper.selectById(id);
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<P> selectBatchIds(Collection<? extends String> idList) {
        return this.mapper.selectBatchIds(idList);
    }

    /*@Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<P> selectByMap(Map<String, Object> columnMap) {
        return this.mapper.selectByMap(columnMap);
    }
     */

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public P selectOne(Wrapper<P> queryWrapper) {
        return this.mapper.selectOne(queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Integer selectCount(Wrapper<P> queryWrapper) {
        return this.mapper.selectCount(queryWrapper);
    }

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

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Boolean isExistsById(String id) {
        QueryWrapper<P> qw = new QueryWrapper<>();
        qw.eq(P.ID, id);

        Integer count = this.mapper.selectCount(qw);
        return count >= 1 ? true : false;
    }

    // 抽象注入
    protected abstract void setMapper(M mapper);

    //protected abstract void setOperUserId(String operUserId);

    //protected abstract void setOperTime(Date operTime);
}
