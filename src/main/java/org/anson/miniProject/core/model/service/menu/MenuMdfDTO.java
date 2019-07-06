package org.anson.miniProject.core.model.service.menu;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import org.anson.miniProject.core.model.dmo.sys.MenuBo;
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

    private static final BeanCopier dto2boCopier = BeanCopier.create(MenuMdfDTO.class, MenuBo.class, false);

    public static MenuBo dto2bo(MenuMdfDTO dto){
        if(dto == null){
            return null;
        }

        MenuBo bo = new MenuBo();

        dto2boCopier.copy(dto, bo, null);

        if(StrUtil.isNotEmpty(dto.getParentMenuId())){
            MenuBo parentMenuBo = new MenuBo();
            parentMenuBo.setId(dto.getParentMenuId());;
            bo.setParentMenuBo(parentMenuBo);
        }

        return bo;
    }
}
