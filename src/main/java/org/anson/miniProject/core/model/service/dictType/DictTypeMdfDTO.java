package org.anson.miniProject.core.model.service.dictType;

import lombok.Data;
import org.anson.miniProject.core.model.bo.sys.MdfDictTypeBO;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

import javax.validation.constraints.NotEmpty;

@Data
public class DictTypeMdfDTO {
    @NotEmpty(message = "请输入数据字典类型id")
    private String id;
    private String no;
    private String name;
    private String description;

    private static final BeanCopier toMdfDictTypeBOCopier = BeanCopier.create(DictTypeMdfDTO.class, MdfDictTypeBO.class, false);

    public static MdfDictTypeBO toMdfDictTypeBOCopier(DictTypeMdfDTO dto) throws InstantiationException, IllegalAccessException {
        return BeanHelper.beanToBean(dto, MdfDictTypeBO.class, toMdfDictTypeBOCopier);
    }
}
