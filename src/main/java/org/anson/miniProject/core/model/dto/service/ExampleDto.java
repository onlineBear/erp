package org.anson.miniProject.core.model.dto.service;

import lombok.Data;
import org.anson.miniProject.core.model.bo.example.ExampleAddBo;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class ExampleDto {
    @NotBlank(message = "请输入编码")
    private String no;

    private static final BeanCopier toLoginBoCopier = BeanCopier.create(ExampleDto.class, ExampleAddBo.class, false);

    public static ExampleAddBo toExampleAddBo(ExampleDto dto) throws InstantiationException, IllegalAccessException {
        return BeanHelper.beanToBean(dto, ExampleAddBo.class, toLoginBoCopier);
    }

    public List<ExampleAddBo> toExampleAddBo(List<ExampleDto> dtoList) throws IllegalAccessException, InstantiationException {
        return BeanHelper.beansToBeans(dtoList, ExampleAddBo.class, toLoginBoCopier);
    }
}
