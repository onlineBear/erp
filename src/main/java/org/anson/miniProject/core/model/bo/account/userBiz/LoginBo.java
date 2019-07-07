package org.anson.miniProject.core.model.bo.account.userBiz;

import cn.hutool.core.collection.IterUtil;
import lombok.Data;
import org.anson.miniProject.core.model.dmo.sys.log.LoginFailedDo;
import org.anson.miniProject.core.model.dmo.sys.log.LoginSuccessDo;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class LoginBo {
    private String no;
    private String encryptedPsd;
    private String loginTypeKey;
    private String clientKey;
    private String ipv4;
    private Date operTime;

    private static final BeanCopier toLoginSuccessDoCopier = BeanCopier.create(LoginBo.class, LoginSuccessDo.class, false);
    private static final BeanCopier toLoginFailedDoCopier = BeanCopier.create(LoginBo.class, LoginFailedDo.class, false);

    public static LoginSuccessDo toLoginSuccessDo(LoginBo bo) throws InstantiationException, IllegalAccessException {
        LoginSuccessDo dmo = BeanHelper.beanToBean(bo, LoginSuccessDo.class, toLoginSuccessDoCopier);
        dmo.setLoginUserNo(bo.getNo());
        return dmo;
    }

    public List<LoginSuccessDo> toLoginSuccessDo(List<LoginBo> boList) throws IllegalAccessException, InstantiationException {
        if (IterUtil.isEmpty(boList)){
            return null;
        }

        List<LoginSuccessDo> doList = new ArrayList<>();

        for (LoginBo bo : boList){
            doList.add(toLoginSuccessDo(bo));
        }

        return doList;
    }

    public static LoginFailedDo toLoginFailedDo(LoginBo bo) throws InstantiationException, IllegalAccessException {
        LoginFailedDo dmo = BeanHelper.beanToBean(bo, LoginFailedDo.class, toLoginFailedDoCopier);
        dmo.setLoginUserNo(bo.getNo());
        return dmo;
    }

    public List<LoginFailedDo> toLoginFailedDo(List<LoginBo> boList) throws IllegalAccessException, InstantiationException {
        if (IterUtil.isEmpty(boList)){
            return null;
        }

        List<LoginFailedDo> doList = new ArrayList<>();

        for (LoginBo bo : boList){
            doList.add(toLoginFailedDo(bo));
        }

        return doList;
    }
}
