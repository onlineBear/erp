package org.anson.miniProject.core.model.dto.service;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddExampleDTO extends BaseDTO{
    // 通用字段
    /**
     * 经度
     */
    private Double longitude;
    /**
     * 纬度
     */
    private Double latitude;


    @NotBlank(message = "请输入编码")
    private String no;

    //private static final BeanCopier toLoginBoCopier = BeanCopier.create(AddExampleDTO.class, AddExampleBO.class, false);
}
