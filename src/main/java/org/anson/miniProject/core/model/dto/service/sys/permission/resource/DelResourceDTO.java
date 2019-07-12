package org.anson.miniProject.core.model.dto.service.sys.permission.resource;

import lombok.Data;
import org.anson.miniProject.core.model.dto.service.BaseDTO;

import javax.validation.constraints.NotBlank;

@Data
public class DelResourceDTO extends BaseDTO {
    @NotBlank(message = "请输入资源id")
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
