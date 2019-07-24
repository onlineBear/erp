package org.anson.miniProject.domain.sys.dictType.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.anson.miniProject.domain.base.BaseDao;
import org.anson.miniProject.tool.helper.InputParamHelper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
@Transactional(rollbackFor = Exception.class)
class DictTypeDao extends BaseDao<DictType, DictTypeMapper> {

    private String operUserId = "createUserId";
    private Date operTime = new Date();

    public String insert(DictType po){
        // 必填检查
        String[] valArray = {po.getNo(), po.getName()};
        String[] errArray = {"请输入数据字典类型编码", "请输入数据字典类型名称"};
        InputParamHelper.required(valArray, errArray);

        // 检查编码
        if(this.isExistByNo(po.getNo())){
            throw new RuntimeException("数据字典类型编码已存在, 编码 = " + po.getNo());
        }

        po.setId(po.getNo());
        po.setCreateUserId(operUserId);
        po.setCreateTime(operTime);
        po.setLastUpdateTime(operTime);

        this.mapper.insert(po);

        return po.getId();
    }

    public void updateById(DictType po){
        // 必填检查
        String[] valArray = {po.getId()};
        String[] errArray = {"请输入数据字典类型id"};
        InputParamHelper.required(valArray, errArray);

        po.setNo(null); // 编码不可修改
        po.setCreateUserId(null);
        po.setCreateTime(null);
        po.setLastUpdateTime(operTime);

        this.mapper.updateById(po);
    }

    public void deleteById(String id){
        this.mapper.deleteById(id);
    }

    // 私有方法(没有事务)
    private Boolean isExistByNo(String no){
        if (StrUtil.isEmpty(no)){
            return false;
        }

        QueryWrapper<DictType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DictType.NO, no);

        Integer count = this.mapper.selectCount(queryWrapper);

        return count >= 1 ? true : false;
    }

    // 注入
    @Override
    public void setMapper(DictTypeMapper mapper) {
        this.mapper = mapper;
    }
}
