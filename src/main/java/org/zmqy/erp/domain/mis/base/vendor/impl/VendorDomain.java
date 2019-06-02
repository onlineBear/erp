package org.zmqy.erp.domain.mis.base.vendor.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.zmqy.erp.constract.mis.enums.ExceptionMsgEnum;
import org.zmqy.erp.domain.mis.base.vendor.IVendorDomain;
import org.zmqy.erp.domain.mis.flow.IFlowDomain;
import org.zmqy.erp.mapper.mis.biz.base.vendor.VendorCertifMapper;
import org.zmqy.erp.mapper.mis.biz.base.vendor.VendorLangMapper;
import org.zmqy.erp.mapper.mis.biz.base.vendor.VendorMapper;
import org.zmqy.erp.framework.exception.model.unI18n.InternalException;
import org.zmqy.erp.framework.exception.model.i18n.none.NonePHException;
import org.zmqy.erp.model.mis.bo.base.vendor.VendorBo;
import org.zmqy.erp.model.mis.bo.base.vendor.VendorCertifBo;
import org.zmqy.erp.model.mis.bo.base.vendor.VendorLangBo;
import org.zmqy.erp.model.mis.entity.base.vendor.Vendor;
import org.zmqy.erp.model.mis.entity.base.vendor.VendorCertif;
import org.zmqy.erp.model.mis.entity.base.vendor.VendorLang;
import org.zmqy.erp.tool.util.common.dataType.ListUtil;
import org.zmqy.erp.tool.util.common.StringUtil;

import java.util.Date;
import java.util.List;

@Component
@Transactional(rollbackFor = Exception.class)
public class VendorDomain implements IVendorDomain {
    @Autowired
    private VendorMapper mapper;
    @Autowired
    private VendorLangMapper vendorLangMapper;
    @Autowired
    private VendorCertifMapper vendorCertifMapper;

    @Override
    public String add(VendorBo bo, String operUserId, Date operTime) throws Exception {
        if(bo == null){
            throw new InternalException("请输入供应商");
        }

        Vendor vendor = VendorBo.bo2entity(bo);

        this.add(vendor, operUserId, operTime);

        if(ListUtil.isEmpty(bo.getVendorLangBoList())){
            throw new InternalException("请输入供应商名称");
        }

        for(VendorLangBo vendorLangBo : bo.getVendorLangBoList()){
            VendorLang vendorLang = VendorLangBo.bo2entity(vendorLangBo);
            vendorLang.setVendorId(vendor.getId());

            this.add(vendorLang, operUserId, operTime);
        }

        if(bo.getVendorCertifBoList() != null && bo.getVendorCertifBoList().size() > 0){
            for(VendorCertifBo vendorCertifBo : bo.getVendorCertifBoList()){
                VendorCertif vendorCertif = VendorCertifBo.bo2entity(vendorCertifBo);
                vendorCertif.setVendorId(vendor.getId());

                this.add(vendorCertif, operUserId, operTime);
            }
        }

        return vendor.getId();
    }

    @Override
    public void batchAdd(List<VendorBo> boList, String operUserId, Date operTime) throws Exception {
        if(ListUtil.isEmpty(boList)){
            return;
        }

        List<Vendor> vendorList = VendorBo.bo2entity(boList);
        this.batchAddVendor(vendorList, operUserId, operTime);
    }

    @Override
    public void mdfById(VendorBo bo, String operUserId, Date operTime) throws Exception {
        if(bo == null){
            throw new InternalException("请输入供应商");
        }

        Vendor vendor = VendorBo.bo2entity(bo);

        this.mdfById(vendor, operUserId, operTime);

        if(ListUtil.isNotEmpty(bo.getVendorLangBoList())){
            for(VendorLangBo vendorLangBo : bo.getVendorLangBoList()){
                VendorLang vendorLang = VendorLangBo.bo2entity(vendorLangBo);
                vendorLang.setVendorId(vendor.getId());

                this.mdfByPPK(vendorLang, operUserId, operTime);
            }
        }

        if(ListUtil.isNotEmpty(bo.getVendorCertifBoList())){
            for(VendorCertifBo vendorCertifBo : bo.getVendorCertifBoList()){
                VendorCertif vendorCertif = VendorCertifBo.bo2entity(vendorCertifBo);
                vendorCertif.setVendorId(vendor.getId());

                this.save(vendorCertif, operUserId, operTime);
            }
        }
    }

    @Override
    public void delVendorCertif(String vendorId, List<String> vendorCertifIdList, String operUserId, Date operTime) throws Exception{
        QueryWrapper<VendorCertif> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(VendorCertif.VENDORID, vendorId)
                    .in(VendorCertif.ID, vendorCertifIdList);

        this.vendorCertifMapper.delete(queryWrapper);
    }

    // 查询
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Vendor getVendorByPPK(String vendorNo){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(Vendor.VENDORNO, vendorNo);

        return this.mapper.selectOne(queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public VendorCertif getVendorCertifByPPK(String vendorId, String certifTypeNo, String certifNo){
        QueryWrapper<VendorCertif> queryWrapper = new QueryWrapper();
        queryWrapper.eq(VendorCertif.VENDORID, vendorId)
                    .eq(VendorCertif.CERTIFTYPENO, certifTypeNo)
                    .eq(VendorCertif.CERTIFNO, certifNo);

        return this.vendorCertifMapper.selectOne(queryWrapper);
    }

    // 内部方法
    // vendor
    public String add(Vendor entity, String operUserId, Date operTime) throws Exception {

        if(StringUtil.isEmpty(entity.getVendorNo())){
            throw new InternalException("请输入供应商编码");
        }

        // 供应商编码重复
        if(this.getVendorByPPK(entity.getVendorNo()) != null){
            throw new NonePHException(ExceptionMsgEnum.REPEAT_VENDORNO);
        }

        entity.setCreateUserId(operUserId);
        entity.setCreateTime(operTime);
        entity.setLastUpdateTime(operTime);

        this.mapper.insert(entity);

        return entity.getId();
    }

    public void batchAddVendor(List<Vendor> vendorList, String operUserId, Date operTime) throws Exception{
        if(ListUtil.isEmpty(vendorList)){
            return;
        }

        // (这里是查数据库一条条, 后面可以改成一条语句来检查, 或者上缓存)

        for(Vendor vendor : vendorList){
            this.add(vendor, operUserId, operTime);
        }
    }

    public void mdfById(Vendor entity, String operUserId, Date operTime) throws Exception {
        if(StringUtil.isEmpty(entity.getId())){
            throw new InternalException("请输入供应商id");
        }

        // 检查供应商编码是否重复
        if(StringUtil.isNotEmpty(entity.getVendorNo())){
            Vendor vendor = this.getVendorByPPK(entity.getVendorNo());

            if(vendor != null && !vendor.getId().equals(entity.getId())){
                throw new NonePHException(ExceptionMsgEnum.REPEAT_VENDORNO);
            }
        }

        entity.setCreateUserId(null);
        entity.setCreateTime(null);
        entity.setLastUpdateTime(operTime);

        this.mapper.updateById(entity);
    }

    // vendorLang
    public String add(VendorLang entity, String operUserId, Date operTime) throws Exception {
        if(StringUtil.isEmpty(entity.getLangId())){
            throw new InternalException("请输入语言id");
        }

        if(StringUtil.isEmpty(entity.getVendorId())){
            throw new InternalException("请输入供应商id");
        }

        if(StringUtil.isEmpty(entity.getVendorName())){
            throw new InternalException("请输入供应商名称");
        }

        if(StringUtil.isEmpty(entity.getVendorShortName())){
            entity.setVendorShortName(StringUtil.subString(entity.getVendorName(), 0, 40));
        }

        entity.setCreateUserId(operUserId);
        entity.setCreateTime(operTime);
        entity.setLastUpdateTime(operTime);

        this.vendorLangMapper.insert(entity);

        return entity.getId();
    }

    public void mdfByPPK(VendorLang entity, String operUserId, Date operTime) throws Exception{
        if(StringUtil.isEmpty(entity.getLangId())){
            throw new InternalException("请输入语言id");
        }

        if(StringUtil.isEmpty(entity.getVendorId())){
            throw new InternalException("请输入供应商id");
        }

        // 检查供应商名称
        if(entity.getVendorName() != null && entity.getVendorName().trim().equals("")){
            throw new InternalException("请输入供应商名称");
        }

        entity.setId(null);
        entity.setCreateUserId(null);
        entity.setCreateTime(null);
        entity.setLastUpdateTime(operTime);

        QueryWrapper<VendorLang> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(VendorLang.VENDORID, entity.getVendorId())
                    .eq(VendorLang.LANGID, entity.getLangId());

        this.vendorLangMapper.update(entity, queryWrapper);
    }

    // 供应商证书
    public String add(VendorCertif entity, String operUserId, Date operTime) throws Exception {
        if(StringUtil.isEmpty(entity.getVendorId())){
            throw new InternalException("请输入供应商id");
        }

        if(StringUtil.isEmpty(entity.getCertifTypeNo())){
            throw new InternalException("请输入证书类型编码");
        }

        if(StringUtil.isEmpty(entity.getCertifNo())){
            throw new InternalException("请输入证书编码");
        }

        if(entity.getExpirationDate() == null){
            throw new InternalException("请输入证书到期日期");
        }

        entity.setCreateUserId(operUserId);
        entity.setCreateTime(operTime);
        entity.setLastUpdateTime(operTime);

        this.vendorCertifMapper.insert(entity);

        return entity.getId();
    }

    public void mdfById(VendorCertif entity, String operUserId, Date operTime) throws Exception {
        if(StringUtil.isEmpty(entity.getId())){
            throw new InternalException("请输入供应商证书id");
        }

        // 检查物理主键是否重复
        if(StringUtil.isNotEmpty(entity.getCertifTypeNo()) || StringUtil.isNotEmpty(entity.getCertifNo())){
            VendorCertif vendorCertif = this.getVendorCertifByPPK(entity.getVendorId(), entity.getCertifTypeNo(), entity.getCertifNo());

            if(vendorCertif != null && !vendorCertif.getId().equals(entity.getId())){
                throw new InternalException("供应商证书类型编码/证书编码重复");
            }
        }

        entity.setCreateUserId(null);
        entity.setCreateTime(null);
        entity.setLastUpdateTime(operTime);

        this.vendorCertifMapper.updateById(entity);
    }

    public String save(VendorCertif entity, String operUserId, Date operTime) throws Exception{
        if(StringUtil.isNotEmpty(entity.getId())){
            this.mdfById(entity, operUserId, operTime);
            return entity.getId();
        }else {
            return this.add(entity, operUserId, operTime);
        }
    }
}
