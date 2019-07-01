package org.anson.miniProject.model.dto.sys.menu;

import lombok.Data;
import org.anson.miniProject.model.bo.sys.menu.MenuMdfBo;
import org.springframework.cglib.beans.BeanCopier;

import javax.validation.constraints.NotEmpty;

@Data
public class MenuMdfDTO {
    @NotEmpty(message = "请输入菜单id")
    private String id;
    private String no;
    private String icon;
    private String name;
    private String description;
    private String parentMenuId;
    private Boolean areDisplay;

    private static final BeanCopier dto2boCopier = BeanCopier.create(MenuMdfDTO.class, MenuMdfBo.class, false);

    public static MenuMdfBo dto2bo(MenuMdfDTO dto){
        if(dto == null){
            return null;
        }

        MenuMdfBo bo = new MenuMdfBo();

        dto2boCopier.copy(dto, bo, null);

        return bo;
    }
}
