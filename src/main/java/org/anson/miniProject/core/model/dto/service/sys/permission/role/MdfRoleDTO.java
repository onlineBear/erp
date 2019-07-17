package org.anson.miniProject.core.model.dto.service.sys.permission.role;

import lombok.Data;
import org.anson.miniProject.core.model.param.sys.permission.role.MdfRoleParam;
import org.anson.miniProject.core.model.dto.service.BaseDTO;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

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

    private static final BeanCopier toMdfRoleParamCopier = BeanCopier.create(MdfRoleDTO.class, MdfRoleParam.class, false);

    public static MdfRoleParam toMdfRoleParam(MdfRoleDTO dto) throws InstantiationException, IllegalAccessException {
        return BeanHelper.beanToBean(dto, MdfRoleParam.class, toMdfRoleParamCopier);
    }
}
