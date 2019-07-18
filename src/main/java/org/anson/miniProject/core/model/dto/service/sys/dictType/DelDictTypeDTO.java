package org.anson.miniProject.core.model.dto.service.sys.dictType;

import lombok.Data;
import org.anson.miniProject.core.model.dto.service.BaseDTO;

import javax.validation.constraints.NotEmpty;

@Data
public class DelDictTypeDTO extends BaseDTO {
    @NotEmpty(message = "请输入数据字典类型id")
    private String id;
}
