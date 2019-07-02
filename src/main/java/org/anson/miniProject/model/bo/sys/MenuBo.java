package org.anson.miniProject.model.bo.sys;

import cn.hutool.core.collection.IterUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Data;
import org.anson.miniProject.model.entity.sys.Menu;
import org.springframework.cglib.beans.BeanCopier;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class MenuBo{
    private String id;
    @NotEmpty(groups = {MenuBo.addMenu.class})
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

    private List<MenuBo> childMenuBoList;
    private MenuBo parentMenuBo;

    private static final BeanCopier entity2boCopier = BeanCopier.create(Menu.class, MenuBo.class, false);
    private static final BeanCopier bo2entityCopier = BeanCopier.create(MenuBo.class, Menu.class, false);

    public static MenuBo entity2bo(Menu entity){
        if(entity == null){
            return null;
        }

        MenuBo bo = new MenuBo();

        entity2boCopier.copy(entity, bo, null);

        if(StrUtil.isNotEmpty(entity.getParentMenuId())){
            MenuBo parentMenuBo = new MenuBo();
            parentMenuBo.setId(entity.getParentMenuId());
            bo.setParentMenuBo(parentMenuBo);
        }

        return bo;
    }

    public static List<MenuBo> entity2bo(List<Menu> entityList){
        if(IterUtil.isEmpty(entityList)){
            return null;
        }

        List<MenuBo> boList = new ArrayList<>();

        for(Menu entity : entityList){
            boList.add(entity2bo(entity));
        }

        return boList;
    }

    public static Menu bo2entity(MenuBo bo){
        if(bo == null){
            return null;
        }

        Menu entity = new Menu();

        bo2entityCopier.copy(bo, entity, null);

        if(bo.getParentMenuBo() != null && bo.getParentMenuBo().getId() != null){
            entity.setParentMenuId(bo.getParentMenuBo().getId());
        }

        return entity;
    }

    public static List<Menu> bo2entity(List<MenuBo> boList){
        if(IterUtil.isEmpty(boList)){
            return null;
        }

        List<Menu> entityList = new ArrayList<>();

        for(MenuBo bo : boList){
            entityList.add(bo2entity(bo));
        }

        return entityList;
    }

    // interface
    public interface addMenu{};
}
