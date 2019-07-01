package org.anson.miniProject.model.service.menu;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import org.anson.miniProject.model.bo.sys.menu.MenuBo;
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

    private static final BeanCopier dto2boCopier = BeanCopier.create(MenuAddDTO.class, MenuBo.class, false);

    public static MenuBo dto2bo(MenuAddDTO dto){
        if(dto == null){
            return null;
        }

        MenuBo bo = new MenuBo();

        dto2boCopier.copy(dto, bo, null);

        if(StrUtil.isNotEmpty(dto.getParentMenuId())){
            MenuBo parentMenuBo = new MenuBo();
            parentMenuBo.setId(dto.getParentMenuId());
            bo.setParentMenuBo(parentMenuBo);
        }

        return bo;
    }
}
