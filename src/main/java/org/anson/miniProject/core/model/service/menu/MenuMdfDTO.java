package org.anson.miniProject.core.model.service.menu;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import org.anson.miniProject.core.model.param.sys.MenuDmo;
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

    private static final BeanCopier dto2boCopier = BeanCopier.create(MenuMdfDTO.class, MenuDmo.class, false);

    public static MenuDmo dto2bo(MenuMdfDTO dto){
        if(dto == null){
            return null;
        }

        MenuDmo bo = new MenuDmo();

        dto2boCopier.copy(dto, bo, null);

        if(StrUtil.isNotEmpty(dto.getParentMenuId())){
            MenuDmo parentMenuDmo = new MenuDmo();
            parentMenuDmo.setId(dto.getParentMenuId());;
            bo.setParentMenuDmo(parentMenuDmo);
        }

        return bo;
    }
}
