package org.anson.miniProject.core.domain.sys.permission.impl;

import cn.hutool.core.collection.IterUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.anson.miniProject.core.domain.sys.IDelRecordDomain;
import org.anson.miniProject.core.domain.sys.permission.IRoleResourceDomain;
import org.anson.miniProject.core.model.dmo.sys.DelRecordDMO;
import org.anson.miniProject.core.model.dmo.sys.permission.roleResource.AddRoleResourceDMO;
import org.anson.miniProject.core.model.po.sys.permission.RoleResource;
import org.anson.miniProject.core.repository.sys.permission.RoleResourceRep;
import org.anson.miniProject.framework.jackson.Jackson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Transactional(rollbackFor = Exception.class)
public class RoleResourceDomain implements IRoleResourceDomain {
    @Autowired
    private RoleResourceRep rep;
    @Autowired
    private IDelRecordDomain delRecordDomain;
    @Autowired
    private Jackson jackson;

    @Override
    public String add(AddRoleResourceDMO dmo, String operUserId, Date operTime) throws Exception {
        RoleResource po = AddRoleResourceDMO.toRoleResource(dmo);
        String id = this.rep.insert(po, operUserId, operTime);
        return id;
    }

    @Override
    public void del(String id, String operUserId, Date operTime) throws Exception {
        // 删除 角色资源关系表
        RoleResource po = this.rep.selectById(id);

        if (po == null){
            return;
        }

        DelRecordDMO delRecordDMO = DelRecordDMO.builder()
                                                .tableName(RoleResource.__TABLENAME)
                                                .pk(id)
                                                .record(jackson.toJson(po))
                                                .build();
        this.delRecordDomain.record(delRecordDMO, operUserId, operTime);

        this.rep.del(id);
    }

    @Override
    public void delByResource(String resId, String operUserId, Date operTime) throws Exception {
        QueryWrapper<RoleResource> qw = new QueryWrapper<>();
        qw.eq(RoleResource.RESOURCEID, resId);
        List<RoleResource> poList = this.rep.selectList(qw);

        if (IterUtil.isEmpty(poList)){
            return;
        }

        List<String> idList = new ArrayList<>();
        List<DelRecordDMO> delRecordDMOList = new ArrayList<>();

        for (RoleResource po : poList){
            DelRecordDMO delRecordDMO = DelRecordDMO.builder()
                    .tableName(RoleResource.__TABLENAME)
                    .pk(po.getId())
                    .record(jackson.toJson(po))
                    .build();
            delRecordDMOList.add(delRecordDMO);
            idList.add(po.getId());
        }

        this.delRecordDomain.record(delRecordDMOList, operUserId, operTime);

        this.rep.del(idList);
    }

    @Override
    public void delByRole(String roleId, String operUserId, Date operTime) throws Exception {
        QueryWrapper<RoleResource> qw = new QueryWrapper<>();
        qw.eq(RoleResource.ROLEID, roleId);
        List<RoleResource> poList = this.rep.selectList(qw);

        if (IterUtil.isEmpty(poList)){
            return;
        }

        List<String> idList = new ArrayList<>();
        List<DelRecordDMO> delRecordDMOList = new ArrayList<>();

        for (RoleResource po : poList){
            DelRecordDMO delRecordDMO = DelRecordDMO.builder()
                    .tableName(RoleResource.__TABLENAME)
                    .pk(po.getId())
                    .record(jackson.toJson(po))
                    .build();
            delRecordDMOList.add(delRecordDMO);
            idList.add(po.getId());
        }

        this.delRecordDomain.record(delRecordDMOList, operUserId, operTime);

        this.rep.del(idList);
    }
}
