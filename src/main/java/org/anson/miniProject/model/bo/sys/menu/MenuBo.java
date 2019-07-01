package org.anson.miniProject.model.bo.sys.menu;

import lombok.Data;
import org.anson.miniProject.model.entity.sys.Menu;
import org.springframework.cglib.beans.BeanCopier;

import java.util.Date;

@Data
public class MenuBo {
    private String id;
    private String no;
    private String icon;
    private String name;
    private String description;
    private String parentMenuId;
    private String clientDictId;
    private String path;
    private Boolean areDisplay;
    private String createUserId;
    private Date createTime;
    private Date lastUpdateTime;

    private static final BeanCopier entity2boCopier = BeanCopier.create(Menu.class, MenuBo.class, false);

    public static MenuBo entity2bo(Menu entity){
        if(entity == null){
            return null;
        }

        MenuBo bo = new MenuBo();

        entity2boCopier.copy(entity, bo, null);

        return bo;
    }
}
