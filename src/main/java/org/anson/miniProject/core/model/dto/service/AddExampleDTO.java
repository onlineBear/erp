package org.anson.miniProject.core.model.dto.service;

import lombok.Data;
import org.anson.miniProject.core.model.bo.AddExampleBO;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

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

    private static final BeanCopier toLoginBoCopier = BeanCopier.create(AddExampleDTO.class, AddExampleBO.class, false);

    public static AddExampleBO toExampleAddBo(AddExampleDTO dto) throws InstantiationException, IllegalAccessException {
        return BeanHelper.beanToBean(dto, AddExampleBO.class, toLoginBoCopier);
    }
}
