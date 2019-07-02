package org.anson.miniProject.model.service.dictType;

import lombok.Data;
import org.anson.miniProject.model.bo.sys.DictTypeBo;
import org.springframework.cglib.beans.BeanCopier;

import javax.validation.constraints.NotEmpty;

@Data
public class DictTypeDelDTO {
    @NotEmpty(message = "请输入数据字典类型id")
    private String id;

    private static final BeanCopier dto2boCopier = BeanCopier.create(DictTypeMdfDTO.class, DictTypeBo.class, false);

    public static DictTypeBo dto2bo(DictTypeMdfDTO dto){
        if(dto == null){
            return null;
        }

        DictTypeBo bo = new DictTypeBo();

        dto2boCopier.copy(dto, bo, null);

        return bo;
    }
}
