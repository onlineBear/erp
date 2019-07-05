package org.anson.miniProject.repository.sys.log;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.anson.miniProject.mapper.sys.log.LoginLogMapper;
import org.anson.miniProject.model.entity.sys.log.LoginLog;
import org.anson.miniProject.repository.BaseRep;
import org.anson.miniProject.tool.helper.IdHelper;
import org.anson.miniProject.tool.helper.InputParamHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
@Transactional(rollbackFor = Exception.class)
public class LoginLogRep extends BaseRep<LoginLog, LoginLogMapper> {

    public String insert(LoginLog entity, String operUserId, Date operTime){
        entity.setUserId(operUserId);
        entity.setOperTime(operTime);

        // 必填检查
        Object[] valArray = {entity.getOperTime(), entity.getLoginTypeKey(), entity.getAreSuccessful()};
        String[] errArray = {"请输入操作时间", "请选择登录类型key", "请输入是否成功"};
        InputParamHelper.required(valArray, errArray);

        // 数据库不存空的字段
        InputParamHelper.nullToEmpty(entity.getUserId(), entity.getLoginUserNo(), entity.getFailReason(),
                                     entity.getIpv4(), entity.getCreateUserId());

        entity.setId(IdHelper.nextSnowflakeId());
        entity.setCreateUserId(operUserId); // operUserId 在这里, 有可能是没有的(登录前是没有的)
        entity.setCreateTime(operTime);
        entity.setLastUpdateTime(operTime);

        this.mapper.insert(entity);
        return entity.getId();
    }

    public void update(LoginLog entity, Date operTime){
        // 必填检查
        Object[] valArray = {entity.getId()};
        String[] errArray = {"请输入登录日志id"};
        InputParamHelper.required(valArray, errArray);

        // 检查id
        /*
        if (!this.isExists(entity.getId())) {
                throw new RuntimeException("没有这个登录登出日志, id = " + entity.getId());
        }
        */

        // 不更新的字段
        entity.setUserId(null);
        entity.setLoginTypeKey(null);
        entity.setOperTime(null);
        entity.setLoginUserNo(null);
        entity.setIpv4(null);
        entity.setLongitude(null);
        entity.setLatitude(null);
        entity.setCreateUserId(null);
        entity.setCreateTime(null);

        //
        entity.setLastUpdateTime(operTime);

        this.mapper.updateById(entity);
    }

    // 查询
    @Transactional(readOnly = true)
    public Boolean isExists(String id){
        QueryWrapper<LoginLog> qw = new QueryWrapper<>();
        qw.eq(LoginLog.ID, id);

        Integer count = this.mapper.selectCount(qw);

        return count>=1?true:false;
    }

    // 注入
    @Autowired
    public void setMapper(LoginLogMapper mapper){
        this.mapper = mapper;
    }
}
