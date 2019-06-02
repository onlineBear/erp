package org.zmqy.erp.model.mis.bo.sys.security;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.cglib.beans.BeanCopier;
import org.zmqy.erp.model.mis.entity.sys.security.RoleMenu;
import org.zmqy.erp.model.mis.entity.sys.security.RoleProgramDra;

import java.util.Date;

@Data
public class RoleProgramDraBo {
    private String id;

    private String programDraId;

    private String roleId;

    private String createUserId;

    private Date createUserTime;

    private Date lastUpdateTime;

    private static final BeanCopier bo2entityCopier = BeanCopier.create(RoleProgramDraBo.class, RoleProgramDra.class, false);

    public static RoleProgramDra bo2entity(RoleProgramDraBo bo){
        if (bo == null){
            return null;
        }

        RoleProgramDra entity = new RoleProgramDra();

        bo2entityCopier.copy(bo, entity, null);

        return entity;
    }

}