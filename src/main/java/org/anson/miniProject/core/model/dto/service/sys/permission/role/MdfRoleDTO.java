package org.anson.miniProject.core.model.dto.service.sys.permission.role;

import lombok.Data;
import org.anson.miniProject.core.model.bo.sys.permission.role.AddRoleBO;
import org.anson.miniProject.core.model.bo.sys.permission.role.MdfRoleBO;
import org.anson.miniProject.core.model.dto.service.BaseDTO;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class MdfRoleDTO extends BaseDTO {
    private String id;

    private String parentRoleId;

    private String no;
    private String name;
    private String description;

    private List<String> leftUserIdList;

    private List<String> leftResIdList;

    // 通用参数
    /**
     * 经度
     */
    private Double longitude;
    /**
     * 纬度
     */
    private Double latitude;

    private static final BeanCopier toMdfRoleBOCopier = BeanCopier.create(MdfRoleDTO.class, MdfRoleBO.class, false);

    public static MdfRoleBO toMdfRoleBO(MdfRoleDTO dmo) throws InstantiationException, IllegalAccessException {
        return BeanHelper.beanToBean(dmo, MdfRoleBO.class, toMdfRoleBOCopier);
    }
}
