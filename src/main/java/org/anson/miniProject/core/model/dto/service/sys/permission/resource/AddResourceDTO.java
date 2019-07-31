package org.anson.miniProject.core.model.dto.service.sys.permission.resource;

import lombok.Data;
import org.anson.miniProject.core.model.dto.service.BaseDTO;

import javax.validation.constraints.NotEmpty;

@Data
public class AddResourceDTO extends BaseDTO {
    private String menuId;

    @NotEmpty(message = "请输入资源名称")
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
}
