package org.anson.miniProject.repository.sys;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.anson.miniProject.mapper.sys.DataDictionaryTypeMapper;
import org.anson.miniProject.model.entity.sys.DictType;
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
public class DictTypeRep implements BaseRepository<DictType> {
    @Autowired
    private DataDictionaryTypeMapper mapper;

    public String insert(DictType entity, String operUserId, Date operTime){
        // 必填检查
        String[] valArray = {entity.getNo(), entity.getName()};
        String[] errArray = {"请输入数据字典类型编码", "请输入数据字典类型名称"};
        InputParamHelper.required(valArray, errArray);

        // 检查编码
        QueryWrapper<DictType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DictType.NO, entity.getNo());

        Integer count = this.mapper.selectCount(queryWrapper);

        if(count > 0){
            throw new RuntimeException("数据字典类型编码已存在, 编码 = " + entity.getNo());
        }

        entity.setId(entity.getNo());
        entity.setCreateUserId(operUserId);
        entity.setCreateTime(operTime);
        entity.setLastUpdateTime(operTime);

        this.mapper.insert(entity);

        return entity.getId();
    }

    public void update(DictType entity, String operUserId, Date operTime){
        // 必填检查
        String[] valArray = {entity.getId()};
        String[] errArray = {"请输入数据字典类型id"};
        InputParamHelper.required(valArray, errArray);

        DictType oldEntity = this.mapper.selectById(entity.getId());

        if(oldEntity == null){
            throw new RuntimeException("没有这个数据字典类型, id = " + entity.getId());
        }

        // 检查编码
        QueryWrapper<DictType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DictType.NO, entity.getNo())
                    .ne(DictType.ID, entity.getId());

        Integer count = this.mapper.selectCount(queryWrapper);

        if(count > 0){
            throw new RuntimeException("数据字典类型编码已存在, 编码 = " + entity.getNo());
        }

        entity.setCreateUserId(null);
        entity.setCreateTime(null);
        entity.setLastUpdateTime(operTime);

        this.mapper.updateById(entity);
    }

    public String save(DictType entity, String operUserId, Date operTime){
        if(StrUtil.isNotEmpty(entity.getId())){
            this.update(entity, operUserId, operTime);
            return entity.getId();
        }else{
            return this.insert(entity, operUserId, operTime);
        }
    }

    public void del(String dictTypeId){
        this.mapper.deleteById(dictTypeId);
    }

    @Override
    @Transactional(readOnly = true)
    public DictType selectById(Serializable id) {
        return this.mapper.selectById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DictType> selectBatchIds(Collection<? extends Serializable> idList) {
        return this.mapper.selectBatchIds(idList);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DictType> selectByMap(Map<String, Object> columnMap) {
        return this.mapper.selectByMap(columnMap);
    }

    @Override
    @Transactional(readOnly = true)
    public DictType selectOne(Wrapper<DictType> queryWrapper) {
        return this.mapper.selectOne(queryWrapper);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer selectCount(Wrapper<DictType> queryWrapper) {
        return this.mapper.selectCount(queryWrapper);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DictType> selectList(Wrapper<DictType> queryWrapper) {
        return this.mapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> selectMaps(Wrapper<DictType> queryWrapper) {
        return this.mapper.selectMaps(queryWrapper);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object> selectObjs(Wrapper<DictType> queryWrapper) {
        return this.mapper.selectObjs(queryWrapper);
    }
}
