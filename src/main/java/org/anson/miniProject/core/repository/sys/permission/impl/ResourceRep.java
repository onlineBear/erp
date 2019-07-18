package org.anson.miniProject.core.repository.sys.permission.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.anson.miniProject.core.mapper.sys.permission.ResourceMapper;
import org.anson.miniProject.core.model.po.sys.permission.Resource;
import org.anson.miniProject.core.repository.BaseRep;
import org.anson.miniProject.core.repository.sys.base.IMenuRep;
import org.anson.miniProject.core.repository.sys.permission.IResourceRep;
import org.anson.miniProject.tool.helper.IdHelper;
import org.anson.miniProject.tool.helper.InputParamHelper;
import org.anson.miniProject.tool.helper.LogicDelHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
@Transactional(rollbackFor = Exception.class)
public class ResourceRep extends BaseRep<Resource, ResourceMapper>
                         implements IResourceRep {

    // 接口命令(需要事务)
    @Override
    public String insert(Resource po, String operUserId, Date operTime) throws Exception{
        // 必填检查
        Object[] valArray = {po.getName()};
        String[] errArray = {"请输入资源名称"};
        InputParamHelper.required(valArray, errArray);

        // 检查 meudId
        if (StrUtil.isEmpty(po.getMenuId())){
            if (!this.menuRep.isExists(po.getMenuId())){
                throw new RuntimeException(String.format("没有这个菜单, 菜单id : %s", po.getMenuId()));
            }
        }

        // 检查 url (url 不为空串时, 是唯一的)
        if (StrUtil.isNotBlank(po.getUrl()) && this.isExistsByUrl(po.getUrl())){
            throw new RuntimeException(String.format("url 重复了, 请检查url, url : %s", po.getUrl()));
        }

        po.setId(IdHelper.nextSnowflakeId());
        po.setCreateUserId(operUserId);
        po.setCreateTime(operTime);
        po.setLastUpdateTime(operTime);

        this.mapper.insert(po);

        return po.getId();
    }

    @Override
    public void update(Resource po, String operUserId, Date operTime) throws Exception{
        // 必填检查
        Object[] valArray = {po.getId()};
        String[] errArray = {"请输入资源id"};
        InputParamHelper.required(valArray, errArray);

        Resource oldPo = this.mapper.selectById(po.getId());

        // 检查 meudId
        if (StrUtil.isNotEmpty(po.getMenuId()) && !po.getMenuId().equals(oldPo.getMenuId())){
            if (!this.menuRep.isExists(po.getMenuId())){
                throw new RuntimeException(String.format("没有这个菜单, 菜单id : %s", po.getMenuId()));
            }
        }

        QueryWrapper<Resource> qw = null;

        // 检查 url (url 不为空串时, 是唯一的)
        if (StrUtil.isNotBlank(po.getUrl()) && !po.getUrl().equals(oldPo.getUrl())){
            qw = new QueryWrapper<>();
            qw.eq(Resource.URL, po.getUrl())
              .ne(Resource.ID, po.getId());

            if (this.mapper.selectCount(qw) >= 1){
                throw new RuntimeException(String.format("url 重复了, 请检查url, url : %s", po.getUrl()));
            }
        }

        po.setCreateUserId(null);
        po.setCreateTime(null);
        po.setLastUpdateTime(operTime);

        this.mapper.updateById(po);
    }

    @Override
    public void del(String id, String operUserId, Date operTime) throws Exception {
        Resource po = this.mapper.selectById(id);

        if (po == null){
            return;
        }

        this.mapper.deleteById(id);
        this.delHelper.recordDelData(po, operUserId, operTime);
    }

    // 接口查询(只读事务)

    // 非接口命令(需要事务)

    // 非接口查询(只读事务)

    // 私有方法(不需要事务)
    private Boolean isExistsByUrl(String url){
        QueryWrapper<Resource> qw = new QueryWrapper<>();
        qw.eq(Resource.URL, url);

        Integer count = this.mapper.selectCount(qw);

        return count >= 1 ? true : false;
    }

    // 注入
    @Autowired
    public void setMapper(ResourceMapper mapper){
        this.mapper = mapper;
    }
    @Autowired
    private LogicDelHelper delHelper;
    @Autowired
    private IMenuRep menuRep;
}
