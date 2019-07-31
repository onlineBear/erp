package org.anson.miniProject.core.model.dto.service.sys.permission.role;

import lombok.Data;
import org.anson.miniProject.core.model.dto.service.BaseDTO;

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
}
