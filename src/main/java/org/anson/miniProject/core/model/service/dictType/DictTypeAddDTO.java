package org.anson.miniProject.core.model.service.dictType;

import lombok.Data;
import org.anson.miniProject.core.model.dmo.sys.DictTypeBo;
import org.springframework.cglib.beans.BeanCopier;

import javax.validation.constraints.NotEmpty;

@Data
public class DictTypeAddDTO {
    @NotEmpty(message = "请输入数据字典类型编码")
    private String no;
    @NotEmpty(message = "请输入数据字典类型名称")
    private String name;
    private String description;

    private static final BeanCopier dto2boCopier = BeanCopier.create(DictTypeAddDTO.class, DictTypeBo.class, false);

    public static DictTypeBo dto2bo(DictTypeAddDTO dto){
        if(dto == null){
            return null;
        }

        DictTypeBo bo = new DictTypeBo();

        dto2boCopier.copy(dto, bo, null);

        return bo;
    }
}
