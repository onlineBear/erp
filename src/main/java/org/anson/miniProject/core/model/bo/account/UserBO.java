package org.anson.miniProject.core.model.bo.account;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.anson.miniProject.core.model.bo.BaseBO;
import org.anson.miniProject.core.model.po.account.User;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

import java.util.Date;

@Data
@Builder
public class UserBO extends BaseBO {
    private String no;
    private String name;
    private String password;
    private String headPortrait;
    private String email;
    private String serveStatus;
    private Date registrationTime;

    // 拥有的角色
    // 拥有的菜单
    // 拥有的资源

    private static final BeanCopier toUserCopier = BeanCopier.create(UserBO.class, User.class, false);

    public User toUser() throws InstantiationException, IllegalAccessException {
        return BeanHelper.beanToBean(this, User.class, toUserCopier);
    }

    @Tolerate
    public UserBO(){}
}
