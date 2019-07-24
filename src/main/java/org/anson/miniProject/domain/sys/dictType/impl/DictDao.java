package org.anson.miniProject.domain.sys.dictType.impl;

import org.anson.miniProject.domain.base.BaseDao;
import org.anson.miniProject.tool.helper.InputParamHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
@Transactional(rollbackFor = Exception.class)
class DictDao extends BaseDao<Dict, DictMapper> {
    public String insert(Dict entity){
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

    // 注入
    @Autowired
    @Override
    protected void setMapper(DictMapper mapper) {
        this.mapper = mapper;
    }

    private String operUserId = "operUserId";
    private Date operTime = new Date();
}
