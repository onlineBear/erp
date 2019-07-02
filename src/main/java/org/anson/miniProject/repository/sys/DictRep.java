package org.anson.miniProject.repository.sys;

import cn.hutool.core.collection.IterUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.anson.miniProject.mapper.sys.DataDictionaryMapper;
import org.anson.miniProject.model.entity.sys.Dict;
import org.anson.miniProject.repository.BaseRepository;
import org.anson.miniProject.tool.helper.InputParamHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
@Transactional(rollbackFor = Exception.class)
public class DictRep implements BaseRepository<Dict> {
    @Autowired
    private DataDictionaryMapper mapper;
    @Autowired
    private DictTypeRep dictTypeRep;

    public String insert(Dict entity, String operUserId, Date operTime){
        // 必填检查
        String[] valArray = {entity.getNo(), entity.getName(), entity.getDictTypeId()};
        String[] errArray = {"请输入数据字典编码", "请输入数据字典名称", "请输入数据字典类型id"};
        InputParamHelper.required(valArray, errArray);

        // 检查编码
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Dict.NO, entity.getNo());

        Integer count = this.mapper.selectCount(queryWrapper);

        if(count > 0){
            throw new RuntimeException("数据字典编码已存在, 编码 = " + entity.getNo());
        }

        // 检查数据字典类型id


        entity.setId(entity.getNo());
        entity.setKey(this.getKey(entity));
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

    public void update(Dict entity, String operUserId, Date operTime){
        // 必填检查
        String[] valArray = {entity.getId()};
        String[] errArray = {"请输入数据字典id"};
        InputParamHelper.required(valArray, errArray);

        // 检查id
        Dict oldEntity = this.mapper.selectById(entity.getId());

        if (oldEntity == null){
            throw new RuntimeException("没有这个数据字典, id = " + entity.getId());
        }

        QueryWrapper<Dict> queryWrapper = null;

        // 检查数据字典类型
        if(StrUtil.isNotEmpty(entity.getDictTypeId()) && !entity.getDictTypeId().equals(oldEntity.getDictTypeId())){
            queryWrapper = new QueryWrapper<>();
        }

        // 检查编码
        if(StrUtil.isNotEmpty(entity.getNo()) && !entity.getNo().equals(oldEntity.getNo())){
            queryWrapper = new QueryWrapper<>();
            queryWrapper.eq(Dict.NO, entity.getNo())
                        .ne(Dict.ID, entity.getId());

            Integer count = this.mapper.selectCount(queryWrapper);

            if(count > 0){
                throw new RuntimeException("数据字典编码已存在, 编码 = " + entity.getNo());
            }
        }

        entity.setKey(null);
        entity.setCreateTime(null);
        entity.setCreateUserId(null);
        entity.setLastUpdateTime(operTime);

        this.mapper.updateById(entity);
    }

    public String save(Dict entity, String operUserId, Date operTime){
        if(StrUtil.isNotEmpty(entity.getId())){
            this.update(entity, operUserId, operTime);
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

    public void del(String dictId){
        this.mapper.deleteById(dictId);
    }

    public void delByDictType(String dictTypeId){
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Dict.DICTTYPEID, dictTypeId);

        this.mapper.delete(queryWrapper);
    }

    // base
    @Override
    @Transactional(readOnly = true)
    public Dict selectById(Serializable id) {
        return this.mapper.selectById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Dict> selectBatchIds(Collection<? extends Serializable> idList) {
        return this.mapper.selectBatchIds(idList);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Dict> selectByMap(Map<String, Object> columnMap) {
        return this.mapper.selectByMap(columnMap);
    }

    @Override
    @Transactional(readOnly = true)
    public Dict selectOne(Wrapper<Dict> queryWrapper) {
        return this.mapper.selectOne(queryWrapper);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer selectCount(Wrapper<Dict> queryWrapper) {
        return this.mapper.selectCount(queryWrapper);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Dict> selectList(Wrapper<Dict> queryWrapper) {
        return this.mapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> selectMaps(Wrapper<Dict> queryWrapper) {
        return this.mapper.selectMaps(queryWrapper);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object> selectObjs(Wrapper<Dict> queryWrapper) {
        return this.mapper.selectObjs(queryWrapper);
    }

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
}
