package org.anson.miniProject.core.model.dto.service.sys.dictType;

import lombok.Data;
import org.anson.miniProject.core.model.dmo.sys.DictTypeDmo;
import org.anson.miniProject.core.model.dto.service.BaseDTO;
import org.springframework.cglib.beans.BeanCopier;

import javax.validation.constraints.NotEmpty;

@Data
public class DelDictTypeDTO extends BaseDTO {
    @NotEmpty(message = "请输入数据字典类型id")
    private String id;

    private static final BeanCopier dto2boCopier = BeanCopier.create(MdfDictTypeDTO.class, DictTypeDmo.class, false);

    public static DictTypeDmo dto2bo(MdfDictTypeDTO dto){
        if(dto == null){
            return null;
        }

        DictTypeDmo bo = new DictTypeDmo();

        dto2boCopier.copy(dto, bo, null);

        return bo;
    }
}
