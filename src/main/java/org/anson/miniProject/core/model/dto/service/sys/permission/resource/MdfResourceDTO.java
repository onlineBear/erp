package org.anson.miniProject.core.model.dto.service.sys.permission.resource;

import lombok.Data;

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
}
