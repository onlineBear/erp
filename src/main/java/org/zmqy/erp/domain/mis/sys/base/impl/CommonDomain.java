package org.zmqy.erp.domain.mis.sys.base.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.zmqy.erp.domain.mis.sys.base.ICommonDomain;
import org.zmqy.erp.domain.mis.sys.base.ICommonTypeDomain;
import org.zmqy.erp.mapper.mis.biz.sys.base.CommonLangMapper;
import org.zmqy.erp.mapper.mis.biz.sys.base.CommonMapper;
import org.zmqy.erp.model.mis.bo.sys.base.CommonBo;
import org.zmqy.erp.model.mis.bo.sys.base.CommonLangBo;
import org.zmqy.erp.model.mis.entity.sys.base.Common;
import org.zmqy.erp.model.mis.entity.sys.base.CommonLang;
import org.zmqy.erp.model.mis.entity.sys.base.CommonType;
import org.zmqy.erp.tool.util.common.StringUtil;
import org.zmqy.erp.tool.util.common.dataType.ListUtil;

import java.util.Date;
import java.util.List;

@Component
@Transactional(rollbackFor = Exception.class)
public class CommonDomain implements ICommonDomain {
    @Autowired
    private CommonMapper mapper;
    @Autowired
    private CommonLangMapper commonLangMapper;
    @Autowired
    private ICommonTypeDomain commonTypeDomain;

    @Override
    public String add(CommonBo bo, String operUserId, Date operTime) throws Exception{
        if(bo == null){
            throw new RuntimeException("commonBo is null");
        }

        Common common = CommonBo.bo2entity(bo);

        this.add(common, operUserId, operTime);

        List<CommonLangBo> commonLangBoList = bo.getCommonLangBoList();

        if(commonLangBoList == null || commonLangBoList.size() <= 0){
            throw new RuntimeException("请输入数据字典名称");
        }

        for(CommonLangBo commonLangBo: commonLangBoList){
            CommonLang commonLang = CommonLangBo.bo2entity(commonLangBo);
            commonLang.setComId(common.getId());

            this.add(commonLang, operUserId, operTime);
        }

        return common.getId();
    }

    @Override
    public void mdfById(CommonBo bo, String operUserId, Date operTime) throws Exception{
        if(bo == null){
            throw new RuntimeException("请输入数据字典");
        }

        Common common = CommonBo.bo2entity(bo);

        this.mdfById(common, operUserId, operTime);

        if(ListUtil.isNotEmpty(bo.getCommonLangBoList())){
            for(CommonLangBo commonLangBo : bo.getCommonLangBoList()){
                CommonLang commonLang = CommonLangBo.bo2entity(commonLangBo);
                commonLang.setComId(common.getId());

                this.save(commonLang, operUserId, operTime);
            }
        }
    }

    @Override
    public String save(CommonBo bo, String operUserId, Date operTime) throws Exception{
        if(bo == null){
            throw new RuntimeException("请输入数据字典");
        }

        if(StringUtil.isEmpty(bo.getId())){
            return this.add(bo, operUserId, operTime);
        }else{
            this.mdfById(bo, operUserId, operTime);
            return bo.getId();
        }
    }

    @Override
    public void delCommonByComType(String comTypeId, List<String> comIdList, String operUserId, Date operTime) throws Exception{
        if(StringUtil.isEmpty(comTypeId)){
            return;
        }

        if(ListUtil.isEmpty(comIdList)){
            return;
        }

        CommonType commonType = this.commonTypeDomain.getById(comTypeId);

        if(commonType == null){
            return;
        }

        // 不能删除
        if(!commonType.getCanDel()){
            throw new RuntimeException("数据字典不能删除");
        }

        QueryWrapper<Common> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Common.COMTYPEID, comTypeId)
                .in(Common.ID, comIdList);

        // 删除common
        this.mapper.delete(queryWrapper);

        // 删除国际化
        this.delCommonLangByCommon(comIdList, operUserId, operTime);
    }

    // // 以下为 类私有方法
    public String add(Common entity, String operUserId, Date operTime) throws Exception{
        // 必填检查
        if(StringUtil.isEmpty(entity.getComTypeId())){
            throw new RuntimeException("请输入数据字典类型");
        }

        if(StringUtil.isEmpty(entity.getComNo())){
            throw new RuntimeException("请输入数据字典编码");
        }

        if(!this.commonTypeDomain.existsCommonTypeId(entity.getComTypeId())){
            throw new RuntimeException("没有这个数据字典类型, 数据字典类型=" + entity.getComTypeId());
        }

        QueryWrapper<Common> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Common.COMNO, entity.getComNo());

        if(this.mapper.selectOne(queryWrapper) != null){
            throw new RuntimeException("数据字典编码重复, " + entity.getComNo());
        }

        entity.setCreateUserId(operUserId);
        entity.setCreateTime(operTime);
        entity.setLastUpdateTime(operTime);

        this.mapper.insert(entity);

        return entity.getId();
    }

    public void mdfById(Common entity, String operUserId, Date operTime) throws Exception{
        if(entity == null){
            throw new RuntimeException("请输入数据字典");
        }

        if(StringUtil.isEmpty(entity.getId())){
            throw new RuntimeException("请输入数据字典id");
        }

        if(entity.getComNo() != null && entity.getComNo().trim().equals("")){
            throw new RuntimeException("请输入数据字典编码");
        }

        QueryWrapper<Common> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Common.COMNO, entity.getComNo());

        Common sys = this.mapper.selectOne(queryWrapper);

        if(sys != null && !sys.getId().equals(entity.getId())){
            throw new RuntimeException("数据字典编码重复");
        }

        entity.setCreateUserId(null);
        entity.setCreateTime(null);
        entity.setLastUpdateTime(operTime);

        this.mapper.updateById(entity);
    }

    // commonLang
    public String add(CommonLang entity, String operUserId, Date operTime){
        if(entity == null){
            throw new RuntimeException("请输入数据字典名称");
        }

        if(StringUtil.isEmpty(entity.getLangId())){
            throw new RuntimeException("请输入语言");
        }

        if(StringUtil.isEmpty(entity.getComName())){
            throw new RuntimeException("请输入数据字典名称");
        }

        if(StringUtil.isEmpty(entity.getComDesc())){
            entity.setComDesc("");
        }

        entity.setCreateUserId(operUserId);
        entity.setCreateTime(operTime);
        entity.setLastUpdateTime(operTime);

        this.commonLangMapper.insert(entity);

        return entity.getId();
    }

    public String save(CommonLang entity, String operUserId, Date operTime){
        if(entity == null){
            throw new RuntimeException("请输入数据字典名称");
        }

        QueryWrapper<CommonLang> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(CommonLang.COMID, entity.getComId())
                    .eq(CommonLang.LANGID, entity.getLangId());

        if(this.commonLangMapper.selectOne(queryWrapper) == null){
            return this.add(entity, operUserId, operTime);
        }else {
            this.mdfByPPK(entity, operUserId, operTime);
            return entity.getId();
        }
    }

    public void mdfByPPK(CommonLang entity, String operUserId, Date operTime){
        if(entity == null){
            throw new RuntimeException("请输入数据字典名称");
        }

        QueryWrapper<CommonLang> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(CommonLang.COMID, entity.getComId())
                .eq(CommonLang.LANGID, entity.getLangId());


        entity.setId(null);
        entity.setCreateUserId(null);
        entity.setCreateTime(null);
        entity.setLastUpdateTime(operTime);

        this.commonLangMapper.update(entity, queryWrapper);
    }

    public void delCommonLangByCommon(String comId, String operUserId, Date operTime){
        if(StringUtil.isEmpty(comId)){
            return;
        }

        QueryWrapper<CommonLang> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(CommonLang.COMID, comId);

        this.commonLangMapper.delete(queryWrapper);
    }

    public void delCommonLangByCommon(List<String> comIdList, String operUserId, Date operTime){
        if(ListUtil.isEmpty(comIdList)){
            return;
        }

        QueryWrapper<CommonLang> queryWrapper = new QueryWrapper<>();
        queryWrapper.in(CommonLang.COMID, comIdList);

        this.commonLangMapper.delete(queryWrapper);
    }
}
