package org.zmqy.erp.domain.mis.sys.security.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.zmqy.erp.domain.mis.sys.base.IMenuDomain;
import org.zmqy.erp.domain.mis.sys.security.IResourceDomain;
import org.zmqy.erp.framework.exception.model.unI18n.InternalException;
import org.zmqy.erp.mapper.mis.biz.sys.security.ResourceLangMapper;
import org.zmqy.erp.mapper.mis.biz.sys.security.ResourceMapper;
import org.zmqy.erp.model.mis.bo.sys.security.ResourceBo;
import org.zmqy.erp.model.mis.bo.sys.security.ResourceLangBo;
import org.zmqy.erp.model.mis.entity.sys.security.Resource;
import org.zmqy.erp.model.mis.entity.sys.security.ResourceLang;
import org.zmqy.erp.model.mis.vo.Response;
import org.zmqy.erp.tool.util.common.StringUtil;
import org.zmqy.erp.tool.util.common.dataType.ListUtil;

import java.util.Date;
import java.util.List;

@Component
@Transactional(rollbackFor = Exception.class)
public class ResourceDomain implements IResourceDomain {
    @Autowired
    private ResourceMapper mapper;
    @Autowired
    private ResourceLangMapper resourceLangMapper;
    @Autowired
    private IMenuDomain menuDomain;

    @Override
    public String add(ResourceBo bo, String operUserId, Date operTime) throws Exception{
        if(bo == null){
            throw new RuntimeException("请输入资源");
        }

        Resource resource = ResourceBo.bo2entity(bo);

        String resId = this.add(resource, operUserId, operTime);

        List<ResourceLangBo> resourceLangBoList = bo.getResourceLangBoList();

        if(resourceLangBoList == null || resourceLangBoList.size() <= 0){
            throw new RuntimeException("请输入资源名称");
        }

        for(ResourceLangBo resourceLangBo : resourceLangBoList){
            ResourceLang resourceLang = ResourceLangBo.bo2entity(resourceLangBo);
            resourceLang.setResId(resId);

            this.add(resourceLang, operUserId, operTime);
        }

        return resource.getId();
    }

    @Override
    public void mdfById(ResourceBo bo, String operUserId, Date operTime) throws Exception{
        if(bo == null){
            throw new RuntimeException("请输入资源");
        }

        Resource resource = ResourceBo.bo2entity(bo);

        this.mdfById(resource, operUserId, operTime);

        if(bo.getResourceLangBoList() != null){
            for(ResourceLangBo resourceLangBo : bo.getResourceLangBoList()){
                ResourceLang resourceLang = ResourceLangBo.bo2entity(resourceLangBo);
                resourceLang.setResId(resource.getId());

                this.saveByPPK(resourceLang, operUserId, operTime);
            }
        }
    }

    @Override
    public void mdfByPPK(ResourceBo bo, String operUserId, Date operTime) throws Exception{
        if(bo == null){
            throw new RuntimeException("请输入资源");
        }

        Resource resource = ResourceBo.bo2entity(bo);

        String resId = this.mdfByPPK(resource, operUserId, operTime);

        if(ListUtil.isNotEmpty(bo.getResourceLangBoList())){
            for(ResourceLangBo resourceLangBo : bo.getResourceLangBoList()){
                ResourceLang resourceLang = ResourceLangBo.bo2entity(resourceLangBo);
                resourceLang.setResId(resId);

                this.saveByPPK(resourceLang, operUserId, operTime);
            }
        }
    }

    @Override
    public String saveById(ResourceBo bo, String operUserId, Date operTime) throws Exception{
        if(bo == null){
            throw new RuntimeException("请输入资源");
        }

        if(StringUtil.isEmpty(bo.getId())){
            return this.add(bo, operUserId, operTime);
        }else{
            this.mdfById(bo, operUserId, operTime);
            return bo.getId();
        }
    }

    @Override
    public String saveByPPK(ResourceBo bo, String operUserId, Date operTime) throws Exception {
        if(bo == null){
            throw new RuntimeException("请输入资源");
        }

        QueryWrapper<Resource> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Resource.RESURL, bo.getResUrl());

        if(this.mapper.selectCount(queryWrapper) <= 0){
            return this.add(bo, operUserId, operTime);
        }else {
            this.mdfByPPK(bo, operUserId, operTime);
            return bo.getId();
        }
    }

    @Override
    public void delByUrl(String url) throws Exception {
        Resource resource = this.getByUrl(url);

        if(resource == null){
            return;
        }

        this.mapper.deleteById(resource.getId());

        this.delResLangByResId(resource.getId());
    }

    @Override
    public void delByUrl(List<String> resUrlList) throws Exception {
        if(ListUtil.isEmpty(resUrlList)){
            return;
        }

        this.mapper.deleteBatchIds(resUrlList);
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Resource getByUrl(String resUrl){
        if(StringUtil.isEmpty(resUrl)){
            throw new RuntimeException("请输入url");
        }

        QueryWrapper<Resource> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Resource.RESURL, resUrl);

        return this.mapper.selectOne(queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Boolean existsResUrl(String resUrl){
        QueryWrapper<Resource> resourceQueryWrapper = new QueryWrapper<>();
        resourceQueryWrapper.eq(Resource.RESURL, resUrl);

        Integer count = this.mapper.selectCount(resourceQueryWrapper);

        return count>0?true:false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Boolean existsRes(String resId){
        QueryWrapper<Resource> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Resource.ID, resId);

        if(this.mapper.selectCount(queryWrapper) > 0){
            return true;
        }

        return false;
    }

    // Resource
    public String add(Resource entity, String operUserId, Date operTime) throws Exception{
        if(StringUtil.isEmpty(entity.getMenuId())){
            throw new RuntimeException("请输入菜单");
        }

        if(StringUtil.isEmpty(entity.getResUrl())){
            throw new RuntimeException("请输入url");
        }

        if(this.existsResUrl(entity.getResUrl())){
            throw new RuntimeException("url重复");
        }

        if(StringUtil.isEmpty(entity.getResOperNo())){
            entity.setResOperNo("");
        }

        entity.setCreateUserId(operUserId);
        entity.setCreateTime(operTime);
        entity.setLastUpdateTime(operTime);

        this.mapper.insert(entity);

        return entity.getId();
    }

    public void mdfById(Resource entity, String operUserId, Date operTime) throws Exception{
        if(StringUtil.isEmpty(entity.getId())){
            throw new RuntimeException("请输入资源id");
        }

        Resource sysResource = this.mapper.selectById(entity.getId());

        // 若修改菜单id
        if(entity.getMenuId() != null && !entity.getMenuId().equals(sysResource.getMenuId())){
            if(!this.menuDomain.existsMenu(entity.getMenuId())){
                throw new RuntimeException("没有这个菜单id");
            }
        }

        if(entity.getResUrl() != null && !entity.getResUrl().equals(sysResource.getResUrl())){
            if(this.existsResUrl(entity.getResUrl())){
                throw new RuntimeException("url重复");
            }
        }

        entity.setCreateUserId(null);
        entity.setCreateTime(null);
        entity.setLastUpdateTime(operTime);

        this.mapper.updateById(entity);
    }

    public String mdfByPPK(Resource entity, String operUserId, Date operTime) throws Exception{
        if(StringUtil.isEmpty(entity.getResUrl())){
            throw new RuntimeException("请输入资源url");
        }

        QueryWrapper<Resource> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Resource.RESURL, entity.getResUrl());

        Resource sysResource = this.mapper.selectOne(queryWrapper);

        if(sysResource != null){
            entity.setId(sysResource.getId());
            this.mdfById(entity, operUserId, operTime);
            return entity.getId();
        }

        return null;
    }

    // ResouceLang
    public String add(ResourceLang entity, String operUserId, Date operTime){
        if(entity == null){
            throw new RuntimeException("请输入资源名称");
        }

        if(StringUtil.isEmpty(entity.getResId())){
            throw new RuntimeException("请输入资源id");
        }

        if(StringUtil.isEmpty(entity.getLangId())){
            throw new RuntimeException("请输入语言");
        }

        if(StringUtil.isEmpty(entity.getResName())){
            throw new RuntimeException("请输入资源名称");
        }

        if(StringUtil.isEmpty(entity.getResDesc())){
            entity.setResDesc("");
        }

        entity.setCreateUserId(operUserId);
        entity.setCreateTime(operTime);
        entity.setLastUpdateTime(operTime);

        this.resourceLangMapper.insert(entity);

        return entity.getId();
    }

    public void mdfByPPK(ResourceLang entity, String operUserId, Date operTime){
        // 物理主键
        if(StringUtil.isEmpty(entity.getResId())){
            throw new RuntimeException("请输入资源id");
        }

        if(StringUtil.isEmpty(entity.getLangId())){
            throw new RuntimeException("请输入语言id");
        }

        if(entity.getResName() != null && entity.getResName().trim().equals("")){
            throw new RuntimeException("请输入资源名称");
        }

        // 什么都没更新
        if(entity.getResName() == null && entity.getResDesc() == null){
            return;
        }

        QueryWrapper<ResourceLang> resourceLangQueryWrapper = new QueryWrapper<>();
        resourceLangQueryWrapper.eq(ResourceLang.RESID, entity.getResId()).eq(ResourceLang.LANGID, entity.getLangId());

        entity.setId(null);
        entity.setResId(null);
        entity.setLangId(null);
        entity.setCreateUserId(null);
        entity.setCreateTime(null);
        entity.setLastUpdateTime(operTime);

        this.resourceLangMapper.update(entity, resourceLangQueryWrapper);
    }

    public String saveByPPK(ResourceLang entity, String operUserId, Date operTime){
        // 物理主键非空
        if(StringUtil.isEmpty(entity.getResId())){
            throw new InternalException("请输入资源id");
        }

        if(StringUtil.isEmpty(entity.getLangId())){
            throw new InternalException("请输入语言id");
        }

        QueryWrapper<ResourceLang> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(ResourceLang.RESID, entity.getResId()).eq(ResourceLang.LANGID, entity.getLangId());

        if(this.resourceLangMapper.selectCount(queryWrapper) == 0){
            return this.add(entity, operUserId, operTime);
        }else{
            this.mdfByPPK(entity, operUserId, operTime);
            return entity.getId();
        }
    }

    public void delResLangByResId(String resId){
        QueryWrapper<ResourceLang> resourceLangQueryWrapper = new QueryWrapper<>();
        resourceLangQueryWrapper.eq(ResourceLang.RESID, resId);
        this.resourceLangMapper.delete(resourceLangQueryWrapper);
    }

    public void delResourceLang(List<String> resIdList){
        QueryWrapper<ResourceLang> resourceLangQueryWrapper = new QueryWrapper<>();
        resourceLangQueryWrapper.in(ResourceLang.RESID, resIdList);
        this.resourceLangMapper.delete(resourceLangQueryWrapper);
    }
}
