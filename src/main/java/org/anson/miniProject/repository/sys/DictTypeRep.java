package org.anson.miniProject.repository.sys;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.anson.miniProject.mapper.sys.DictTypeMapper;
import org.anson.miniProject.model.entity.sys.DictType;
import org.anson.miniProject.repository.BaseRep;
import org.anson.miniProject.tool.helper.InputParamHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
@Transactional(rollbackFor = Exception.class)
public class DictTypeRep extends BaseRep<DictType, DictTypeMapper> {

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

    // 查询
    public Boolean isExistsById(String id){
        QueryWrapper<DictType> dictTypeQw = new QueryWrapper<>();
        dictTypeQw.eq(DictType.ID, id);

        Integer count = this.mapper.selectCount(dictTypeQw);

        if (count == 1){
            return true;
        }

        return false;
    }

    // set
    @Autowired
    public void setMapper(DictTypeMapper mapper){
        this.mapper = mapper;
    }
}
