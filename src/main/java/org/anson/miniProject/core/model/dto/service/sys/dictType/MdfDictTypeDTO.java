package org.anson.miniProject.core.model.dto.service.sys.dictType;

import lombok.Data;
import org.anson.miniProject.core.model.param.sys.dictType.MdfDictTypeParam;
import org.anson.miniProject.core.model.dto.service.BaseDTO;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

import javax.validation.constraints.NotEmpty;

@Data
public class MdfDictTypeDTO extends BaseDTO {
    @NotEmpty(message = "请输入数据字典类型id")
    private String id;
    private String no;
    private String name;
    private String description;

    private static final BeanCopier toParamCopier = BeanCopier.create(MdfDictTypeDTO.class, MdfDictTypeParam.class, false);

    public static MdfDictTypeParam toParam(MdfDictTypeDTO dto) throws InstantiationException, IllegalAccessException {
        return BeanHelper.beanToBean(dto, MdfDictTypeParam.class, toParamCopier);
    }
}
