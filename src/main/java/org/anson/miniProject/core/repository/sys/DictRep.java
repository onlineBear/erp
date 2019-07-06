package org.anson.miniProject.core.repository.sys;

import cn.hutool.core.collection.IterUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.anson.miniProject.core.mapper.sys.DictMapper;
import org.anson.miniProject.core.model.po.sys.Dict;
import org.anson.miniProject.core.repository.BaseRep;
import org.anson.miniProject.tool.helper.InputParamHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Component
@Transactional(rollbackFor = Exception.class)
public class DictRep extends BaseRep<Dict, DictMapper> {

    public String insert(Dict entity, String operUserId, Date operTime){
        // 必填检查
        String[] valArray = {entity.getNo(), entity.getName(), entity.getDictTypeId()};
        String[] errArray = {"请输入数据字典编码", "请输入数据字典名称", "请输入数据字典类型id"};
        InputParamHelper.required(valArray, errArray);

        // 检查数据字典类型id
        if(!this.dictTypeRep.isExistsById(entity.getDictTypeId())){
            throw new RuntimeException(String.format("没有这个数据字典类型, id : %s", entity.getDictTypeId()));
        }

        // 检查编码
        QueryWrapper<Dict> dictQw = new QueryWrapper<>();
        dictQw.eq(Dict.DICTTYPEID, entity.getDictTypeId())
                    .eq(Dict.NO, entity.getNo());

        Integer count = this.mapper.selectCount(dictQw);

        if(count > 0){
            throw new RuntimeException(String.format("数据字典编码已存在, 编码 : %s, 数据字典类型id : %s", entity.getNo(), entity.getDictTypeId()));
        }

        entity.setId(entity.getNo());
        entity.setDictKey(this.getKey(entity));
        entity.setCreateUserId(operUserId);
        entity.setCreateTime(operTime);
        entity.setLastUpdateTime(operTime);

        this.mapper.insert(entity);

        return entity.getId();
    }

    public void insert(List<Dict> entityList, String operUserId, Date operTime){
        if (IterUtil.isNotEmpty(entityList)){
            for (Dict entity : entityList){
                this.insert(entity, operUserId, operTime);
            }
        }
    }

    public void update(Dict entity, Date operTime){
        // 必填检查
        String[] valArray = {entity.getId()};
        String[] errArray = {"请输入数据字典id"};
        InputParamHelper.required(valArray, errArray);

        // 检查id
        Dict oldEntity = this.mapper.selectById(entity.getId());

        if (oldEntity == null){
            throw new RuntimeException("没有这个数据字典, id = " + entity.getId());
        }

        QueryWrapper<Dict> dictQw = null;

        // 检查数据字典类型
        if(StrUtil.isNotEmpty(entity.getDictTypeId()) && !entity.getDictTypeId().equals(oldEntity.getDictTypeId())){
            if(!this.dictTypeRep.isExistsById(entity.getDictTypeId())){
                throw new RuntimeException(String.format("没有这个数据字典类型, id : %s", entity.getDictTypeId()));
            }
        }

        // 检查编码
        if(StrUtil.isNotEmpty(entity.getNo()) && !entity.getNo().equals(oldEntity.getNo())){
            String newDictTypeId = StrUtil.isEmpty(entity.getDictTypeId()) ? oldEntity.getDictTypeId() : entity.getDictTypeId();

            dictQw = new QueryWrapper<>();
            dictQw.eq(Dict.NO, entity.getNo())
                  .eq(Dict.DICTTYPEID, newDictTypeId)
                  .ne(Dict.ID, entity.getId());

            Integer count = this.mapper.selectCount(dictQw);

            if(count > 0){
                throw new RuntimeException(String.format("数据字典编码已存在, 编码 : %s, 数据字典id : %s ", entity.getNo(), newDictTypeId));
            }
        }

        entity.setDictKey(null);
        entity.setCreateTime(null);
        entity.setCreateUserId(null);
        entity.setLastUpdateTime(operTime);

        this.mapper.updateById(entity);
    }

    public String save(Dict entity, String operUserId, Date operTime){
        if(StrUtil.isNotEmpty(entity.getId())){
            this.update(entity, operTime);
            return entity.getId();
        }else {
            return this.insert(entity, operUserId, operTime);
        }
    }

    public void save(List<Dict> entityList, String operUserId, Date operTime){
        if (IterUtil.isNotEmpty(entityList)){
            for (Dict entity : entityList){
                this.save(entity, operUserId, operTime);
            }
        }
    }

    public void del(String dictId) {
        // 删除记录
        this.mapper.deleteById(dictId);
    }

    public void del(Collection<? extends String> idList) {
        this.mapper.deleteBatchIds(idList);
    }

    public void delByDictType(String dictTypeId){
        // 删除数据
        this.mapper.deleteById(dictTypeId);
    }

    // 查询
    @Transactional(readOnly = true)
    public Boolean isExistsById(Serializable id){
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Dict.ID, id);

        Integer count = this.mapper.selectCount(queryWrapper);

        return count>0?true:false;
    }

    @Transactional(readOnly = true)
    public List<Dict> selByDictTypeId(String dictTypeId){
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Dict.DICTTYPEID, dictTypeId);

        return this.mapper.selectList(queryWrapper);
    }

    // private
    private String getKey(Dict dict){
        StringBuilder sb = new StringBuilder();
        sb.append(dict.getDictTypeId()).append("-").append(dict.getId());
        return sb.toString();
    }

    // 注入
    @Autowired
    public void setMapper(DictMapper mapper){
        this.mapper = mapper;
    }

    @Autowired
    private DictTypeRep dictTypeRep;
}
