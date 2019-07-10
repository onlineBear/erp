package org.anson.miniProject.core.model.dto.service;

import lombok.Data;

@Data
public abstract class BaseDTO {
    /**
     * 经度
     */
    private Double longitude;
    /**
     * 纬度
     */
    private Double latitude;
}
