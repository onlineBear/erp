package org.anson.miniProject.core.model.dto.service;

import lombok.Data;
import org.anson.miniProject.core.model.bo.example.ExampleAddBo;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

import javax.validation.constraints.NotBlank;

@Data
public class AddExampleDTO extends BaseDTO{
    @NotBlank(message = "请输入编码")
    private String no;

    private static final BeanCopier toLoginBoCopier = BeanCopier.create(AddExampleDTO.class, ExampleAddBo.class, false);

    public static ExampleAddBo toExampleAddBo(AddExampleDTO dto) throws InstantiationException, IllegalAccessException {
        return BeanHelper.beanToBean(dto, ExampleAddBo.class, toLoginBoCopier);
    }
}
