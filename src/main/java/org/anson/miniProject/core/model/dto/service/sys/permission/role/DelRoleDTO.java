package org.anson.miniProject.core.model.dto.service.sys.permission.role;

import lombok.Data;
import org.anson.miniProject.core.model.dto.service.BaseDTO;

@Data
public class DelRoleDTO extends BaseDTO {
    private String id;

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
