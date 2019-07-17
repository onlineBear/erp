package org.anson.miniProject.core.repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.anson.miniProject.core.model.po.BasePO;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface IBaseRep<P extends BasePO, M extends BaseMapper<P>> {
    P selectById(String id);
    List<P> selectBatchIds(Collection<? extends String> idList);
    P selectOne(Wrapper<P> queryWrapper);
    Integer selectCount(Wrapper<P> queryWrapper);
    List<P> selectList(Wrapper<P> queryWrapper);
    Boolean isExists(String id);

    /*
    // 根据外键查询
    List<P> selByxx(String xxId);
    Boolean isExistsByNo(String no);


    String insert(P po, String operUserId, Date operTime) throws Exception;
    void insert(List<P> poList, String operUserId, Date operTime);

    void update(P po, String operUserId, Date operTime) throws Exception;
    void update(List<P> po, String operUserId, Date operTime) throws Exception;

    String save(P po, String operUserId, Date operTime) throws Exception;
    void save(List<P> po, String operUserId, Date operTime) throws Exception;

    void del(String id, String operUserId, Date operTime) throws Exception;
    void del(Collection<? extends Serializable> idList, String operUserId, Date operTime) throws Exception;
    // 根据外键删除
    void delByxx(String xxId, String operUserId, Date operTime) throws Exception;
    */
}
