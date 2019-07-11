package org.anson.miniProject.core.repository.sys.permission;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.anson.miniProject.core.mapper.sys.permission.ResourceMapper;
import org.anson.miniProject.core.model.po.sys.permission.Resource;
import org.anson.miniProject.core.repository.BaseRep;
import org.anson.miniProject.tool.helper.IdHelper;
import org.anson.miniProject.tool.helper.InputParamHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
@Transactional(rollbackFor = Exception.class)
public class ResourceRep extends BaseRep<Resource, ResourceMapper> {

    // 更新等 (需要事务)
    public String insert(Resource po, String operUserId, Date operTime){
        // 必填检查
        Object[] valArray = {po.getName()};
        String[] errArray = {"请输入资源名称"};
        InputParamHelper.required(valArray, errArray);

        // 检查 meudId
        if (StrUtil.isNotBlank(po.getMenuId())){

        }

        QueryWrapper<Resource> qw = null;

        // 检查 url (url 不为空串时, 是唯一的)
        if (StrUtil.isNotBlank(po.getUrl())){
            qw = new QueryWrapper<>();
            qw.eq(Resource.URL, po.getUrl());

            if (this.mapper.selectCount(qw) >= 1){
                throw new RuntimeException(String.format("url 重复了, 请检查url, url : {}", po.getUrl()));
            }
        }

        po.setId(IdHelper.nextSnowflakeId());
        po.setCreateUserId(operUserId);
        po.setCreateTime(operTime);
        po.setLastUpdateTime(operTime);

        this.mapper.insert(po);

        return po.getId();
    }

    public void update(Resource po, String operUserId, Date operTime){
        // 必填检查
        Object[] valArray = {po.getId()};
        String[] errArray = {"请输入资源id"};
        InputParamHelper.required(valArray, errArray);

        Resource oldPo = this.mapper.selectById(po.getId());

        // 检查 meudId
        if (StrUtil.isNotBlank(po.getMenuId()) && !po.getMenuId().equals(oldPo.getMenuId())){

        }

        QueryWrapper<Resource> qw = null;

        // 检查 url (url 不为空串时, 是唯一的)
        if (StrUtil.isNotBlank(po.getUrl()) && !po.getUrl().equals(oldPo.getUrl())){
            qw = new QueryWrapper<>();
            qw.eq(Resource.URL, po.getUrl())
              .ne(Resource.ID, po.getId());

            if (this.mapper.selectCount(qw) >= 1){
                throw new RuntimeException(String.format("url 重复了, 请检查url, url : {}", po.getUrl()));
            }
        }

        po.setCreateUserId(null);
        po.setCreateTime(null);
        po.setLastUpdateTime(operTime);

        this.mapper.updateById(po);
    }

    public void del(String id){
        this.mapper.deleteById(id);
    }

    // 查询 (不需要事务)

    // 注入
    @Autowired
    public void setMapper(ResourceMapper mapper){
        this.mapper = mapper;
    }
}
