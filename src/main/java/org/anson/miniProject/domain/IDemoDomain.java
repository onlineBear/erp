package org.anson.miniProject.domain;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface IDemoDomain<T> {

    // 新增 option
    String add(T bo, String operUserId, Date operTime) throws Exception;
    // 批量新增 option
    Integer batchAdd(List<T> boList, String operUserId, Date operTime) throws Exception;

    // 根据主键id更新 option
    String mdfById(T bo, String operUserId, Date operTime) throws Exception;
    // 根据物理主键更新 option
    void mdfByPPK(T bo, String operUserId, Date operTime) throws Exception;
    // 批量根据主键id更新 option
    Integer batchMdfById(List<T> boList, String operUserId, Date operTime) throws Exception;
    // 批量根据物理主键更新 option
    Integer batchMdfByPPK(List<T> boList, String operUserId, Date operTime) throws Exception;

    // 根据主键id保存 option
    String saveById(T bo, String operUserId, Date operTime) throws Exception;
    // 根据物理主键保存 option
    void saveByPPK(T bo, String operUserId, Date operTime) throws Exception;
    // 批量根据id保存 option
    Integer batchSaveById(List<T> boList, String operUserId, Date operTime) throws Exception;
    // 批量根据物理主键保存 option
    Integer batchSaveByPPK(List<T> boList, String operUserId, Date operTime) throws Exception;

    // 根据主键id删除 option
    Integer delById(String id, String operUserId, Date operTime) throws Exception;
    // 根据物理主键id删除 option
    Integer delByPPK(T bo, String operUserId, Date operTime) throws Exception;
    // 根据外键id删除 option
    Integer delByFK(String fkId, String operUserId, Date operTime) throws Exception;
    // 批量根据主键id删除 option
    Integer batchDelById(List<String> idList, String operUserId, Date operTime) throws Exception;
    // 批量根据物理主键删除 option
    Integer batchDelByPPK(List<T> boList, String operUserId, Date operTime) throws Exception;
    // 批量根据外键删除 option
    Integer batchDelByFK(String fkId, String operUserId, Date operTime) throws Exception;

    // 专用查询
    T getById(String id);

    T getByPPK();

    // 通用查询
    T selById(String id);
    List<T> selBatchIds(Collection<? extends Serializable> idList);
    Integer selCount(QueryWrapper<T> queryWrapper);
    T selOne(QueryWrapper<T> queryWrapper);
    List<T> selList(QueryWrapper<T> queryWrapper);
}
