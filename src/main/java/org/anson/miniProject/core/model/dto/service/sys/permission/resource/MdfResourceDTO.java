package org.anson.miniProject.core.model.dto.service.sys.permission.resource;

import lombok.Data;
import org.anson.miniProject.core.model.bo.sys.permission.resource.MdfResourceBO;
import org.anson.miniProject.core.model.dto.service.sys.permission.role.AddRoleDTO;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

@Data
public class MdfResourceDTO {
    private String id;

    private String menuId;

    private String name;
    private String description;
    private String url;

    // 通用参数
    /**
     * 经度
     */
    private Double longitude;
    /**
     * 纬度
     */
    private Double latitude;

    private static final BeanCopier toMdfResourceBOCopier = BeanCopier.create(MdfResourceDTO.class, MdfResourceBO.class, false);

    public static MdfResourceBO toMdfResourceBO(AddRoleDTO dmo) throws InstantiationException, IllegalAccessException {
        return BeanHelper.beanToBean(dmo, MdfResourceBO.class, toMdfResourceBOCopier);
    }
}
