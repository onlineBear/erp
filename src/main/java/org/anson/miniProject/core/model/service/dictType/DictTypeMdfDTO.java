package org.anson.miniProject.core.model.service.dictType;

import lombok.Data;
import org.anson.miniProject.core.model.dmo.sys.DictTypeDmo;
import org.springframework.cglib.beans.BeanCopier;

import javax.validation.constraints.NotEmpty;

@Data
public class DictTypeMdfDTO {
    @NotEmpty(message = "请输入数据字典类型id")
    private String id;
    private String no;
    private String name;
    private String description;

    private static final BeanCopier dto2boCopier = BeanCopier.create(DictTypeMdfDTO.class, DictTypeDmo.class, false);

    public static DictTypeDmo dto2bo(DictTypeMdfDTO dto){
        if(dto == null){
            return null;
        }

        DictTypeDmo bo = new DictTypeDmo();

        dto2boCopier.copy(dto, bo, null);

        return bo;
    }
}
