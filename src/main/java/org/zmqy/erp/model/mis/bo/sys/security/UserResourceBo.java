package org.zmqy.erp.model.mis.bo.sys.security;

import lombok.Data;
import org.springframework.cglib.beans.BeanCopier;
import org.zmqy.erp.model.mis.entity.sys.security.UserResource;

import java.util.Date;

@Data
public class UserResourceBo {
    private String id;

    private String userId;

    private String resId;

    private String createUserId;

    private Date createTime;

    private Date lastUpdateTime;

    private static final BeanCopier bo2entityCopier = BeanCopier.create(UserResourceBo.class, UserResource.class, false);

    public static UserResource bo2entity(UserResourceBo bo){
        if (bo == null){
            return null;
        }

        UserResource entity = new UserResource();

        bo2entityCopier.copy(bo, entity, null);

        return entity;
    }
}
