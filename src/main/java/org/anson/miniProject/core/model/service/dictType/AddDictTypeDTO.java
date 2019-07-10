package org.anson.miniProject.core.model.service.dictType;

import lombok.Data;
import org.anson.miniProject.core.model.bo.sys.AddDictTypeBO;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

import javax.validation.constraints.NotEmpty;

@Data
public class AddDictTypeDTO {
    @NotEmpty(message = "请输入数据字典类型编码")
    private String no;
    @NotEmpty(message = "请输入数据字典类型名称")
    private String name;
    private String description;

    private static final BeanCopier toAddDictTypeBOCopier = BeanCopier.create(AddDictTypeDTO.class, AddDictTypeBO.class, false);

    public static AddDictTypeBO toAddDictTypeBO(AddDictTypeDTO dto) throws InstantiationException, IllegalAccessException {
        return BeanHelper.beanToBean(dto, AddDictTypeBO.class, toAddDictTypeBOCopier);
    }
}
