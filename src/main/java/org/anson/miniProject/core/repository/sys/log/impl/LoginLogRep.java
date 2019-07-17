package org.anson.miniProject.core.repository.sys.log.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.anson.miniProject.core.mapper.sys.log.LoginLogMapper;
import org.anson.miniProject.core.model.po.sys.log.LoginLog;
import org.anson.miniProject.core.repository.BaseRep;
import org.anson.miniProject.core.repository.sys.log.ILoginLogRep;
import org.anson.miniProject.tool.helper.IdHelper;
import org.anson.miniProject.tool.helper.InputParamHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
@Transactional(rollbackFor = Exception.class)
public class LoginLogRep extends BaseRep<LoginLog, LoginLogMapper>
                         implements ILoginLogRep {

    // 接口命令(需要事务)
    @Override
    public String insert(LoginLog po, String operUserId, Date operTime){
        po.setUserId(operUserId);
        po.setOperTime(operTime);

        // 必填检查
        Object[] valArray = {po.getOperTime(), po.getLoginTypeKey(), po.getAreSuccessful()};
        String[] errArray = {"请输入操作时间", "请选择登录类型key", "请输入是否成功"};
        InputParamHelper.required(valArray, errArray);

        // 数据库不存空的字段
        InputParamHelper.nullToEmpty(po.getUserId(), po.getLoginUserNo(), po.getFailReason(),
                                     po.getIpv4(), po.getCreateUserId());

        po.setId(IdHelper.nextSnowflakeId());
        po.setCreateUserId(operUserId); // operUserId 在这里, 有可能是没有的(登录前是没有的)
        po.setCreateTime(operTime);
        po.setLastUpdateTime(operTime);

        this.mapper.insert(po);
        return po.getId();
    }

    @Override
    public void update(LoginLog po, Date operTime){
        // 必填检查
        Object[] valArray = {po.getId()};
        String[] errArray = {"请输入登录日志id"};
        InputParamHelper.required(valArray, errArray);

        // 检查id
        /*
        if (!this.isExists(entity.getId())) {
                throw new RuntimeException("没有这个登录登出日志, id = " + entity.getId());
        }
        */

        // 不更新的字段
        po.setLoginTypeKey(null);
        po.setOperTime(null);
        po.setLoginUserNo(null);
        po.setIpv4(null);
        po.setLongitude(null);
        po.setLatitude(null);
        po.setCreateUserId(null);
        po.setCreateTime(null);

        //
        po.setLastUpdateTime(operTime);

        this.mapper.updateById(po);
    }

    // 接口查询(只读事务)
    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Boolean isExists(String id){
        QueryWrapper<LoginLog> qw = new QueryWrapper<>();
        qw.eq(LoginLog.ID, id);

        Integer count = this.mapper.selectCount(qw);

        return count>=1?true:false;
    }

    // 非接口命令(需要事务)

    // 非接口查询(只读事务)

    // 私有方法(没有事务)

    // 注入
    @Override
    @Autowired
    public void setMapper(LoginLogMapper mapper){
        this.mapper = mapper;
    }
}
