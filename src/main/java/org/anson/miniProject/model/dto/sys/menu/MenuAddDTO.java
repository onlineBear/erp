package org.anson.miniProject.model.dto.sys.menu;

import lombok.Data;
import org.anson.miniProject.model.bo.sys.menu.MenuAddBo;
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

    private static final BeanCopier bo2entityCopier = BeanCopier.create(MenuAddDTO.class, MenuAddBo.class, false);

    public static MenuAddBo dto2bo(MenuAddDTO dto){
        if(dto == null){
            return null;
        }

        MenuAddBo bo = new MenuAddBo();

        bo2entityCopier.copy(dto, bo, null);

        return bo;
    }
}
