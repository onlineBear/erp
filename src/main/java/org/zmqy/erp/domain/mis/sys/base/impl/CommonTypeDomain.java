package org.zmqy.erp.domain.mis.sys.base.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.zmqy.erp.domain.mis.sys.base.ICommonDomain;
import org.zmqy.erp.domain.mis.sys.base.ICommonTypeDomain;
import org.zmqy.erp.mapper.mis.biz.sys.base.CommonTypeLangMapper;
import org.zmqy.erp.mapper.mis.biz.sys.base.CommonTypeMapper;
import org.zmqy.erp.model.mis.bo.sys.base.CommonBo;
import org.zmqy.erp.model.mis.bo.sys.base.CommonTypeBo;
import org.zmqy.erp.model.mis.bo.sys.base.CommonTypeLangBo;
import org.zmqy.erp.model.mis.entity.sys.base.Common;
import org.zmqy.erp.model.mis.entity.sys.base.CommonType;
import org.zmqy.erp.model.mis.entity.sys.base.CommonTypeLang;
import org.zmqy.erp.tool.util.common.StringUtil;
import org.zmqy.erp.tool.util.common.dataType.ListUtil;

import java.util.Date;
import java.util.List;

@Component
@Transactional(rollbackFor = Exception.class)
public class CommonTypeDomain implements ICommonTypeDomain {
    @Autowired
    private CommonTypeMapper mapper;
    @Autowired
    private CommonTypeLangMapper commonTypeLangMapper;
    @Autowired
    private ICommonDomain commonDomain;

    @Override
    public String add(CommonTypeBo bo, String operUserId, Date operTime) throws Exception{
        if(bo == null){
            throw new RuntimeException("数据字典类型为空");
        }

        CommonType commonType = CommonTypeBo.bo2entity(bo);

        this.add(commonType, operUserId, operTime);

        List<CommonTypeLangBo> commonTypeLangBoList = bo.getCommonTypeLangBoList();

        if(ListUtil.isEmpty(commonTypeLangBoList)){
            throw new RuntimeException("请输入数据字典类型名称");
        }

        for(CommonTypeLangBo commonTypeLangBo : commonTypeLangBoList){
            CommonTypeLang commonTypeLang = CommonTypeLangBo.bo2entity(commonTypeLangBo);
            commonTypeLang.setComTypeId(commonType.getId());

            this.add(commonTypeLang, operUserId, operTime);
        }

        if(ListUtil.isNotEmpty(bo.getCommonBoList())){
            for(CommonBo commonBo : bo.getCommonBoList()){
                commonBo.setComTypeId(commonType.getId());

                this.commonDomain.add(commonBo, operUserId, operTime);
            }
        }

        return commonType.getId();
    }

    @Override
    public String mdfById(CommonTypeBo bo, String operUserId, Date operTime) throws Exception{
        if(bo == null){
            throw new RuntimeException("请输入数据字典类型");
        }

        CommonType commonType = CommonTypeBo.bo2entity(bo);

        this.mdfById(commonType, operUserId, operTime);

        if(bo.getCommonTypeLangBoList() != null && bo.getCommonTypeLangBoList().size() > 0){
            for(CommonTypeLangBo commonTypeLangBo : bo.getCommonTypeLangBoList()){
                CommonTypeLang commonTypeLang = CommonTypeLangBo.bo2entity(commonTypeLangBo);
                commonTypeLang.setComTypeId(commonType.getId());

                this.save(commonTypeLang, operUserId, operTime);
            }
        }

        commonType = this.mapper.selectById(commonType.getId());

        if(ListUtil.isNotEmpty(bo.getCommonBoList())){
            // 若 不允许修改
            if(!commonType.getCanMdf()){
                throw new RuntimeException("不允许修改数据字典");
            }
            // 若 不允许新增
            for(CommonBo commonBo : bo.getCommonBoList()){
                // 若 不允许新增
                if(!commonType.getCanAdd() && StringUtils.isNotEmpty(commonBo.getId())){
                    throw new RuntimeException("不允许新增数据字典");
                }

                commonBo.setComTypeId(commonType.getId());
                this.commonDomain.save(commonBo, operUserId, operTime);
            }
        }

        return bo.getId();
    }

    @Override
    public String save(CommonTypeBo bo, String operUserId, Date operTime) throws Exception{
        if(bo == null){
            throw new RuntimeException("请输入数据字典类型");
        }

        if(StringUtil.isNotEmpty(bo.getId())){
            this.mdfById(bo, operUserId, operTime);
            return bo.getId();
        }else {
            return this.add(bo, operUserId, operTime);
        }
    }

    @Override
    public void delCommon(String comTypeId, List<String> comIdList, String operUserId, Date operTime) throws Exception{
        this.commonDomain.delCommonByComType(comTypeId, comIdList, operUserId, operTime);
    }

    // 查询

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public CommonType getById(String comTypeId) {
        return this.mapper.selectById(comTypeId);
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Boolean existsCommonTypeNo(String commonTypeNo) throws Exception{
        QueryWrapper<CommonType> commonTypeQueryWrapper = new QueryWrapper<>();
        commonTypeQueryWrapper.eq(CommonType.COMTYPENO, commonTypeNo);

        if(this.mapper.selectCount(commonTypeQueryWrapper) > 0){
            return true;
        }

        return false;
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Boolean existsCommonTypeId(String commonTypeId) throws Exception{
        if(this.mapper.selectById(commonTypeId) == null){
            return false;
        }else {
            return true;
        }
    }


    // 以下为 类私有方法
    public String add(CommonType entity, String operUserId, Date operTime) throws Exception{
        if(StringUtil.isEmpty(entity.getComTypeNo())){
            throw new RuntimeException("请输入数据类型编码");
        }

        if(this.existsCommonTypeNo(entity.getComTypeNo())){
            throw new RuntimeException("已有这个数据类型编码, 数据类型编码=" + entity.getComTypeNo());
        }

        // 默认值
        if(entity.getCanAdd() == null){
            entity.setCanAdd(true);
        }

        if(entity.getCanMdf() == null){
            entity.setCanMdf(true);
        }

        if(entity.getCanDel() == null){
            entity.setCanDel(false);
        }

        entity.setCreateUserId(operUserId);
        entity.setCreateTime(operTime);
        entity.setLastUpdateTime(operTime);

        this.mapper.insert(entity);

        return entity.getId();
    }

    public void mdfById(CommonType entity, String operUserId, Date operTime) throws Exception{
        if(StringUtil.isEmpty(entity.getId())){
            throw new RuntimeException("请输入数据字典类型id");
        }

        if(entity.getComTypeNo() != null && entity.getComTypeNo().trim().equals("")){
            throw new RuntimeException("请输入数据字典类型编码");
        }

        entity.setCreateUserId(null);
        entity.setCreateTime(null);
        entity.setLastUpdateTime(operTime);

        this.mapper.updateById(entity);
    }

    public String add(CommonTypeLang entity, String operUserId, Date operTime){
        if(entity == null){
            throw new RuntimeException("请输入数据字典类型名称");
        }

        if(StringUtil.isEmpty(entity.getLangId())){
            throw new RuntimeException("请输入语言");
        }

        if(StringUtil.isEmpty(entity.getComTypeName())){
            throw new RuntimeException("请输入数据字典类型名称");
        }

        if(StringUtil.isEmpty(entity.getComTypeDesc())){
            entity.setComTypeDesc("");
        }

        entity.setCreateUserId(operUserId);
        entity.setCreateTime(operTime);
        entity.setLastUpdateTime(operTime);

        this.commonTypeLangMapper.insert(entity);

        return entity.getId();
    }

    public void mdf(CommonTypeLang entity, String operUserId, Date operTime){
        if(entity == null){
            throw new RuntimeException("请输入数据字典类型");
        }

        if(StringUtil.isEmpty(entity.getComTypeId())){
            throw new RuntimeException("请输入数据字典类型id");
        }

        if(StringUtil.isEmpty(entity.getLangId())){
            throw new RuntimeException("请输入语言id");
        }

        if(entity.getComTypeName() != null && entity.getComTypeName().trim().equals("")){
            throw new RuntimeException("请输入数据字典类型名称");
        }

        entity.setId(null);
        entity.setCreateUserId(null);
        entity.setCreateTime(null);
        entity.setLastUpdateTime(operTime);

        QueryWrapper<CommonTypeLang> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(CommonTypeLang.COMTYPEID, entity.getComTypeId()).eq(CommonTypeLang.LANGID, entity.getLangId());

        this.commonTypeLangMapper.update(entity, queryWrapper);
    }

    public String save(CommonTypeLang entity, String operUserId, Date operTime) throws Exception{
        if(entity == null){
            throw new RuntimeException("请输入数据字典类型");
        }

        if(StringUtil.isEmpty(entity.getComTypeId())){
            throw new RuntimeException("请输入数据字典类型id");
        }

        if(StringUtil.isEmpty(entity.getLangId())){
            throw new RuntimeException("请输入语言id");
        }

        QueryWrapper<CommonTypeLang> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(CommonTypeLang.COMTYPEID, entity.getComTypeId())
                    .eq(CommonTypeLang.LANGID, entity.getLangId());

        if(this.commonTypeLangMapper.selectCount(queryWrapper) == 0){
            return this.add(entity, operUserId, operTime);
        }else {
            this.mdf(entity, operUserId, operTime);
            return entity.getId();
        }

    }
}
