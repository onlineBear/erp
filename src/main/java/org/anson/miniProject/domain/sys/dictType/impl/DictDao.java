package org.anson.miniProject.domain.sys.dictType.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.anson.miniProject.domain.base.BaseDao;
import org.anson.miniProject.domain.internal.deletedRecord.DelHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Transactional(rollbackFor = Exception.class)
class DictDao extends BaseDao<Dict, DictMapper> {
    public String insert(Dict dict){
        dict.setId(dict.getNo());
        dict.setCreateTime(operTime);
        dict.setUpdateTime(operTime);

        this.mapper.insert(dict);

        return dict.getId();
    }

    public void batchInsert(List<Dict> dictList){
        if (CollUtil.isEmpty(dictList)){
            return;
        }

        for (Dict dict : dictList){
            this.insert(dict);
        }
    }

    public void updateById(Dict dict){
        dict.setNo(null); // 编码不可修改
        dict.setCreateTime(null);
        dict.setUpdateTime(operTime);

        this.mapper.updateById(dict);
    }

    public void batchUpdateById(List<Dict> dictList){
        if (CollUtil.isEmpty(dictList)){
            return;
        }

        for (Dict dict : dictList){
            this.updateById(dict);
        }
    }

    public void deleteByDictType(String dictTypeId, String dictId) throws Exception{
        QueryWrapper<Dict> qw = new QueryWrapper<>();
        qw.eq(Dict.DICTTYPEID, dictTypeId)
            .eq(Dict.ID, dictId);

        Dict dict = this.mapper.selectOne(qw);

        if (dict == null){
            return;
        }

        this.delHelper.recordDelData(dict);
        this.mapper.deleteById(dict.getId());
    }

    public void deleteByDictType(String dictTypeId, List<String> dictIdList) throws Exception{
        QueryWrapper<Dict> qw = new QueryWrapper<>();
        qw.eq(Dict.DICTTYPEID, dictTypeId)
                .in(Dict.ID, dictIdList);

        List<Dict> dictList = this.mapper.selectList(qw);

        if (CollUtil.isEmpty(dictList)){
            return;
        }

        for (Dict dict : dictList){
            this.delHelper.recordDelData(dict);
        }

        this.mapper.deleteBatchIds(dictIdList);

    }

    public void deleteByDictType(String dictTypeId) throws Exception{
        QueryWrapper<Dict> qw = new QueryWrapper<>();
        qw.eq(Dict.DICTTYPEID, dictTypeId);

        List<Dict> dictList = this.mapper.selectList(qw);

        if (CollUtil.isEmpty(dictList)){
            return;
        }

        List<String> dictIdList = new ArrayList<>();

        for (Dict dict : dictList){
            this.delHelper.recordDelData(dict);
            dictIdList.add(dict.getId());
        }

        this.mapper.deleteBatchIds(dictIdList);
    }

    // 注入
    @Autowired
    @Override
    protected void setMapper(DictMapper mapper) {
        this.mapper = mapper;
    }
    @Autowired
    private DelHelper delHelper;

    private String operUserId = "operUserId";
    private Date operTime = new Date();
}
