package org.anson.miniProject.core.model.param.sys;

import cn.hutool.core.collection.IterUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Data;
import org.anson.miniProject.core.model.po.sys.Menu;
import org.springframework.cglib.beans.BeanCopier;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class MenuDmo {
    private String id;
    @NotEmpty(groups = {MenuDmo.addMenu.class})
    private String no;
    private String icon;
    private String name;
    private String description;
    private String clientDictId;
    private String path;
    private Boolean areDisplay;

    private String createUserId;
    private Date createTime;
    private Date lastUpdateTime;

    private List<MenuDmo> childMenuDmoList;
    private MenuDmo parentMenuDmo;

    private static final BeanCopier entity2boCopier = BeanCopier.create(Menu.class, MenuDmo.class, false);
    private static final BeanCopier bo2entityCopier = BeanCopier.create(MenuDmo.class, Menu.class, false);

    public static MenuDmo entity2bo(Menu entity){
        if(entity == null){
            return null;
        }

        MenuDmo bo = new MenuDmo();

        entity2boCopier.copy(entity, bo, null);

        if(StrUtil.isNotEmpty(entity.getParentMenuId())){
            MenuDmo parentMenuDmo = new MenuDmo();
            parentMenuDmo.setId(entity.getParentMenuId());
            //MenuDmo.setParentMenuDmo(parentMenuDmo);
        }

        return bo;
    }

    public static List<MenuDmo> entity2bo(List<Menu> entityList){
        if(IterUtil.isEmpty(entityList)){
            return null;
        }

        List<MenuDmo> boList = new ArrayList<>();

        for(Menu entity : entityList){
            boList.add(entity2bo(entity));
        }

        return boList;
    }

    public static Menu bo2entity(MenuDmo bo){
        if(bo == null){
            return null;
        }

        Menu entity = new Menu();

        bo2entityCopier.copy(bo, entity, null);
        /*
        if(this.getParentMenuDmo() != null && this.getParentMenuDmo().getId() != null){
            entity.setParentMenuId(this.getParentMenuDmo().getId());
        }
        */

        return entity;
    }

    public static List<Menu> bo2entity(List<MenuDmo> boList){
        if(IterUtil.isEmpty(boList)){
            return null;
        }

        List<Menu> entityList = new ArrayList<>();

        for(MenuDmo bo : boList){
            entityList.add(bo2entity(bo));
        }

        return entityList;
    }

    // interface
    public interface addMenu{}
}
