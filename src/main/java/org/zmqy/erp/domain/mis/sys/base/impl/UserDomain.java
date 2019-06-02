package org.zmqy.erp.domain.mis.sys.base.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.zmqy.erp.constract.mis.enums.ExceptionMsgEnum;
import org.zmqy.erp.domain.mis.sys.base.IUserDomain;
import org.zmqy.erp.mapper.mis.biz.sys.base.UserMapper;
import org.zmqy.erp.framework.exception.model.unI18n.InternalException;
import org.zmqy.erp.framework.exception.model.i18n.none.NonePHException;
import org.zmqy.erp.model.mis.bo.sys.base.UserBo;
import org.zmqy.erp.model.mis.entity.sys.base.User;
import org.zmqy.erp.tool.helper.encrypt.EncryptHelper;
import org.zmqy.erp.tool.util.common.StringUtil;

import java.util.Date;

@Component
public class UserDomain implements IUserDomain {

    @Autowired
    private UserMapper mapper;

    @Transactional(rollbackFor = Exception.class)
    public String add(UserBo bo, String operUserId, Date operTime) throws Exception{
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    public void mdfById(UserBo bo, String operUserId, Date operTime) throws Exception{
        return;
    }

    /**
     * 验证用户密码是否正确
     * @param userNo
     * @param password
     * @throws Exception
     */
    public String verify(String userNo, String password) throws Exception{
        if (StringUtil.isEmpty(userNo)) {
            throw new InternalException("请输入用户编码");
        }

        if (StringUtil.isEmpty(password)) {
            throw new InternalException("请输入密码");
        }

        // 是否有这个用户
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(User.USERNO, userNo);

        User user = this.mapper.selectOne(queryWrapper);

        if (user == null) {
            throw new NonePHException(ExceptionMsgEnum.NOTEXISTS_USERNO);
        }

        // 密码加密
        String encryptPsd = EncryptHelper.getEncryptPsd(password);

        if (!encryptPsd.equals(user.getPassword())) {
            throw new NonePHException(ExceptionMsgEnum.PSDNOTCORRECT);
        }

        return user.getId();
    }

    // 以下为 类私有方法
}
