package org.anson.miniProject.service.account.model;

import lombok.Data;

@Data
public class LogoutDTO {
    // 通用字段
    /**
     * 经度
     */
    private Double longitude;
    /**
     * 纬度
     */
    private Double latitude;
}
