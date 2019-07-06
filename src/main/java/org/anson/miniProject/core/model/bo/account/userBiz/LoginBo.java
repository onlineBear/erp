package org.anson.miniProject.core.model.bo.account.userBiz;

import lombok.Data;
import org.anson.miniProject.core.model.dmo.sys.log.BeginLoginDo;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

import java.util.Date;
import java.util.List;

@Data
public class LoginBo {
    private String no;
    private String encryptedPsd;
    private String loginTypeKey;
    private String ipv4;
    private Date operTime;

    private static final BeanCopier toBeginLoginDoCopier = BeanCopier.create(LoginBo.class, BeginLoginDo.class, false);

    public static BeginLoginDo toBeginLoginDo(LoginBo bo) throws InstantiationException, IllegalAccessException {
        return BeanHelper.beanToBean(bo, BeginLoginDo.class, toBeginLoginDoCopier);
    }

    public List<BeginLoginDo> toBeginLoginDo(List<LoginBo> boList) throws IllegalAccessException, InstantiationException {
        return BeanHelper.beansToBeans(boList, BeginLoginDo.class, toBeginLoginDoCopier);
    }
}
