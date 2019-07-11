package org.anson.miniProject.core.domain.sys.permission.impl;

import cn.hutool.core.collection.IterUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.anson.miniProject.core.domain.sys.IDelRecordDomain;
import org.anson.miniProject.core.domain.sys.permission.IUserRoleDomain;
import org.anson.miniProject.core.model.dmo.sys.DelRecordDMO;
import org.anson.miniProject.core.model.dmo.sys.permission.userRole.AddUserRoleDMO;
import org.anson.miniProject.core.model.po.sys.permission.UserRole;
import org.anson.miniProject.core.repository.sys.permission.UserRoleRep;
import org.anson.miniProject.framework.jackson.Jackson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Transactional(rollbackFor = Exception.class)
public class UserRoleDomain implements IUserRoleDomain {
    @Autowired
    private UserRoleRep rep;
    @Autowired
    private IDelRecordDomain delRecordDomain;
    @Autowired
    private Jackson jackson;

    @Override
    public String add(AddUserRoleDMO dmo, String operUserId, Date operTime) throws Exception {
        UserRole po = AddUserRoleDMO.toUserRole(dmo);
        String id = this.rep.insert(po, operUserId, operTime);
        return id;
    }

    @Override
    public void del(String id, String operUserId, Date operTime) throws Exception {
        UserRole po = this.rep.selectById(id);

        if (po == null){
            return;
        }

        DelRecordDMO delRecordDMO = DelRecordDMO.builder()
                                                .tableName(UserRole.__TABLENAME)
                                                .pk(id)
                                                .record(jackson.toJson(po))
                                                .build();
        this.delRecordDomain.record(delRecordDMO, operUserId, operTime);

        this.rep.del(id);
    }

    @Override
    public void delByUser(String userId, String operUserId, Date operTime) throws Exception {
        QueryWrapper<UserRole> qw = new QueryWrapper<>();
        qw.eq(UserRole.USERID, userId);
        List<UserRole> poList = this.rep.selectList(qw);

        if (IterUtil.isEmpty(poList)){
            return;
        }

        List<String> idList = new ArrayList<>();
        List<DelRecordDMO> delRecordDMOList = new ArrayList<>();

        for (UserRole po : poList){
            DelRecordDMO delRecordDMO = DelRecordDMO.builder()
                                                    .tableName(UserRole.__TABLENAME)
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
        QueryWrapper<UserRole> qw = new QueryWrapper<>();
        qw.eq(UserRole.ROLEID, roleId);
        List<UserRole> poList = this.rep.selectList(qw);

        if (IterUtil.isEmpty(poList)){
            return;
        }

        List<String> idList = new ArrayList<>();
        List<DelRecordDMO> delRecordDMOList = new ArrayList<>();

        for (UserRole po : poList){
            DelRecordDMO delRecordDMO = DelRecordDMO.builder()
                    .tableName(UserRole.__TABLENAME)
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
