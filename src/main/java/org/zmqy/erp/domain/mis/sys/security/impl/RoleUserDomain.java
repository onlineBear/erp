package org.zmqy.erp.domain.mis.sys.security.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.zmqy.erp.domain.mis.sys.security.IRoleUserDomain;
import org.zmqy.erp.mapper.mis.biz.sys.security.RoleUserMapper;
import org.zmqy.erp.model.mis.bo.sys.security.RoleUserBo;
import org.zmqy.erp.model.mis.entity.sys.security.RoleUser;
import org.zmqy.erp.tool.util.common.StringUtil;
import org.zmqy.erp.tool.util.common.dataType.ListUtil;

import java.util.Date;
import java.util.List;

@Component
public class RoleUserDomain implements IRoleUserDomain {
    @Autowired
    private RoleUserMapper mapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void mdf(String roleId, List<String> allUserIdList, String operUserId, Date operTime) throws Exception {
        if(StringUtil.isEmpty(roleId)){
            throw new RuntimeException("请输入角色id");
        }

        // 删除不存在的
        QueryWrapper<RoleUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.notIn(RoleUser.USERID, allUserIdList)
                    .eq(RoleUser.ROLEID, roleId);

        this.mapper.delete(queryWrapper);

        // saveById
        if(ListUtil.isNotEmpty(allUserIdList)){
            for(String userId : allUserIdList){
                RoleUserBo bo = new RoleUserBo();
                bo.setRoleId(roleId);
                bo.setUserId(userId);
                this.save(bo, operUserId, operTime);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String add(RoleUserBo bo, String operUserId, Date operTime) throws Exception{
        if(bo == null){
            throw new RuntimeException("请输入角色用户");
        }

        RoleUser roleUser = RoleUserBo.bo2entity(bo);

        return this.add(roleUser, operUserId, operTime);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String save(RoleUserBo bo, String operUserId, Date operTime) throws Exception{
        if(bo == null){
            throw new RuntimeException("请输入角色用户");
        }

        RoleUser roleUser = RoleUserBo.bo2entity(bo);

        return this.save(roleUser, operUserId, operTime);
    }

    public void delByRole(String roleId) throws Exception{
        if(StringUtil.isEmpty(roleId)){
            throw new RuntimeException("请输入角色id");
        }

        QueryWrapper<RoleUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(RoleUser.ROLEID, roleId);

        this.mapper.delete(queryWrapper);
    }

    // 以下为 类私有方法
    @Transactional(rollbackFor = Exception.class)
    public String add(RoleUser entity, String operUserId, Date operTime) throws Exception{
        if(StringUtil.isEmpty(entity.getRoleId())){
            throw new RuntimeException("请输入角色id");
        }

        if(StringUtil.isEmpty(entity.getUserId())){
            throw new RuntimeException("请输入用户id");
        }

        entity.setId(null);
        entity.setCreateUserId(operUserId);
        entity.setCreateTime(operTime);
        entity.setLastUpdateTime(operTime);

        this.mapper.insert(entity);

        return entity.getId();
    }

    @Transactional(rollbackFor = Exception.class)
    public String save(RoleUser entity, String operUserId, Date operTime) throws Exception{
        if(StringUtil.isEmpty(entity.getRoleId())){
            throw new RuntimeException("请输入角色id");
        }

        if(StringUtil.isEmpty(entity.getUserId())){
            throw new RuntimeException("请输入用户id");
        }

        QueryWrapper<RoleUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(RoleUser.ROLEID, entity.getRoleId()).eq(RoleUser.USERID, entity.getUserId());

        RoleUser roleUser = this.mapper.selectOne(queryWrapper);

        if(roleUser == null){
            return this.add(entity, operUserId, operTime);
        }else {
            return roleUser.getId();
        }
    }
}
