package org.anson.miniProject.core.repository.sys.base.impl;

import cn.hutool.core.collection.IterUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.anson.miniProject.core.mapper.sys.DictMapper;
import org.anson.miniProject.core.model.po.sys.base.Dict;
import org.anson.miniProject.core.repository.BaseRep;
import org.anson.miniProject.core.repository.sys.base.IDictRep;
import org.anson.miniProject.tool.helper.InputParamHelper;
import org.anson.miniProject.tool.helper.LogicDelHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Component
@Transactional(rollbackFor = Exception.class)
public class DictRep extends BaseRep<Dict, DictMapper>
                     implements IDictRep {
    // 接口命令(需要事务)
    @Override
    public String insert(Dict entity, String operUserId, Date operTime) throws Exception{
        // 必填检查
        Object[] valArray = {entity.getNo(), entity.getName(), entity.getDictTypeId()};
        String[] errArray = {"请输入数据字典编码", "请输入数据字典名称", "请输入数据字典类型id"};
        InputParamHelper.required(valArray, errArray);

        // 检查数据字典类型id
        if(!this.dictTypeRep.isExists(entity.getDictTypeId())){
            throw new RuntimeException(String.format("没有这个数据字典类型, id : %s", entity.getDictTypeId()));
        }

        // 检查编码
        if(this.isExistsByDictTypeIdAndNo(entity.getDictTypeId(), entity.getNo())){
            throw new RuntimeException(String.format("数据字典编码已存在, 编码 : %s, 数据字典类型id : %s", entity.getNo(), entity.getDictTypeId()));
        }

        entity.setId(entity.getNo());
        entity.setDictKey(this.getDictKey(entity));
        entity.setCreateUserId(operUserId);
        entity.setCreateTime(operTime);
        entity.setLastUpdateTime(operTime);

        this.mapper.insert(entity);

        return entity.getId();
    }

    @Override
    public void insert(List<Dict> entityList, String operUserId, Date operTime) throws Exception{
        if (IterUtil.isNotEmpty(entityList)){
            for (Dict entity : entityList){
                this.insert(entity, operUserId, operTime);
            }
        }
    }

    @Override
    public void update(Dict entity, Date operTime) throws Exception{
        // 必填检查
        String[] valArray = {entity.getId()};
        String[] errArray = {"请输入数据字典id"};
        InputParamHelper.required(valArray, errArray);

        // 检查id
        Dict oldEntity = this.mapper.selectById(entity.getId());

        if (oldEntity == null){
            throw new RuntimeException("没有这个数据字典, id = " + entity.getId());
        }

        // 编码和数据字典类型不可修改
        entity.setDictTypeId(null);
        entity.setNo(null);
        entity.setDictKey(null);

        entity.setCreateTime(null);
        entity.setCreateUserId(null);
        entity.setLastUpdateTime(operTime);

        this.mapper.updateById(entity);
    }

    @Override
    public void del(String dictId, String operUserId, Date operTime) throws Exception {
        Dict po = this.mapper.selectById(dictId);

        if (po == null){
            return;
        }

        this.mapper.deleteById(dictId);
        this.delHelper.recordDelData(po, operUserId, operTime);
    }

    @Override
    public void del(Collection<? extends String> idList, String operUserId, Date operTime) throws Exception {
        List<Dict> poList = this.mapper.selectBatchIds(idList);

        if (IterUtil.isEmpty(poList)){
            return;
        }

        this.mapper.deleteBatchIds(idList);

        for (Dict po : poList){
            this.delHelper.recordDelData(po, operUserId, operTime);
        }
    }

    @Override
    public void delByDictType(String dictTypeId, String operUserId, Date operTime) throws Exception {
        List<Dict> poList = this.selByDictTypeId(dictTypeId);

        if (IterUtil.isEmpty(poList)){
            return;
        }

        List<String> idList = new ArrayList<>();

        for (Dict po : poList){
            this.delHelper.recordDelData(po, operUserId, operTime);
            idList.add(po.getId());
        }

        this.mapper.deleteBatchIds(idList);
    }

    // 接口查询(只读事务)

    @Override
    @Transactional(readOnly = true)
    public List<Dict> selByDictTypeId(String dictTypeId){
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Dict.DICTTYPEID, dictTypeId);

        return this.mapper.selectList(queryWrapper);
    }

    // 非接口命令(需要事务)

    // 非接口查询(只读事务)

    // 私有方法(没有事务)
    private String getDictKey(Dict dict){
        StringBuilder sb = new StringBuilder();
        sb.append(dict.getDictTypeId()).append("-").append(dict.getId());
        return sb.toString();
    }

    private Boolean isExistsByDictTypeIdAndNo(String dictTypeId, String no){
        QueryWrapper<Dict> dictQw = new QueryWrapper<>();
        dictQw.eq(Dict.DICTTYPEID, dictTypeId)
            .eq(Dict.NO, no);

        Integer count = this.mapper.selectCount(dictQw);

        return count >= 1 ? true : false;
    }

    // 注入
    @Autowired
    public void setMapper(DictMapper mapper){
        this.mapper = mapper;
    }

    @Autowired
    private DictTypeRep dictTypeRep;
    @Autowired
    private LogicDelHelper delHelper;
}
