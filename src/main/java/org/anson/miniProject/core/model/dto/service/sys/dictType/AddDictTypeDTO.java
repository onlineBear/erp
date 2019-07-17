package org.anson.miniProject.core.model.dto.service.sys.dictType;

import lombok.Data;
import org.anson.miniProject.core.model.param.sys.dictType.AddDictTypeParam;
import org.anson.miniProject.core.model.dto.service.BaseDTO;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

import javax.validation.constraints.NotEmpty;

@Data
public class AddDictTypeDTO extends BaseDTO {
    @NotEmpty(message = "请输入数据字典类型编码")
    private String no;
    @NotEmpty(message = "请输入数据字典类型名称")
    private String name;
    private String description;

    private static final BeanCopier toParamCopier = BeanCopier.create(AddDictTypeDTO.class, AddDictTypeParam.class, false);

    public static AddDictTypeParam toParam(AddDictTypeDTO dto) throws InstantiationException, IllegalAccessException {
        return BeanHelper.beanToBean(dto, AddDictTypeParam.class, toParamCopier);
    }
}
