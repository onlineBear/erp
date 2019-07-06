package org.anson.miniProject.core.model.dmo.account.user;

import lombok.Data;
import org.anson.miniProject.core.model.po.account.User;
import org.springframework.cglib.beans.BeanCopier;

import java.util.Date;

@Data
public class RegisterBo {
    private String no;
    private String name;
    private String password;
    private String headPortrait;
    private String email;
    private String serveStatus;
    private Date registrationTime;

    private static final BeanCopier bo2entityCopier = BeanCopier.create(RegisterBo.class, User.class, false);

    public static User bo2entity(RegisterBo bo){
        if(bo == null){
            return null;
        }

        User entity = new User();

        bo2entityCopier.copy(bo, entity, null);

        return entity;
    }
}
