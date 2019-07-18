package org.anson.miniProject.core.model.service.menu;

import lombok.Data;
import org.anson.miniProject.core.model.param.sys.base.menu.AddMenuParam;
import org.springframework.cglib.beans.BeanCopier;

import javax.validation.constraints.NotEmpty;

@Data
public class MenuAddDTO {
    @NotEmpty(message = "请输入菜单编码")
    private String no;
    private String icon;
    private String name;
    private String description;
    @NotEmpty(message = "请选择上级菜单")
    private String parentMenuId;
    private Boolean areDisplay;

    private static final BeanCopier dto2boCopier = BeanCopier.create(MenuAddDTO.class, AddMenuParam.class, false);

    public static AddMenuParam dto2bo(MenuAddDTO dto){
        if(dto == null){
            return null;
        }

        AddMenuParam bo = new AddMenuParam();

        dto2boCopier.copy(dto, bo, null);

        return bo;
    }
}
