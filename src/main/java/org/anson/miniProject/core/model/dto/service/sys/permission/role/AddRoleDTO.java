package org.anson.miniProject.core.model.dto.service.sys.permission.role;

import lombok.Data;
import org.anson.miniProject.core.model.dmo.sys.permission.role.AddRoleParam;
import org.anson.miniProject.core.model.dto.service.BaseDTO;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class AddRoleDTO extends BaseDTO {
    private String parentRoleId;

    @NotEmpty(message = "请输入角色编码")
    private String no;
    @NotEmpty(message = "请输入角色名称")
    private String name;
    private String description;

    private List<String> resourceIdList;
    private List<String> userIdList;

    // 通用参数
    /**
     * 经度
     */
    private Double longitude;
    /**
     * 纬度
     */
    private Double latitude;

    private static final BeanCopier toAddRoleParamCopier = BeanCopier.create(AddRoleDTO.class, AddRoleParam.class, false);

    public static AddRoleParam toAddRoleBO(AddRoleDTO dmo) throws InstantiationException, IllegalAccessException {
        return BeanHelper.beanToBean(dmo, AddRoleParam.class, toAddRoleParamCopier);
    }
}
