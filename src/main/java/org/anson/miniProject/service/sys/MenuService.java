package org.anson.miniProject.service.sys;

import lombok.extern.slf4j.Slf4j;
import org.anson.miniProject.core.model.service.menu.*;
import org.anson.miniProject.domain.sys.menu.IMenuDMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class MenuService {
    @Autowired
    private IMenuDMService menuDMService;

    public MenuAddVo addMenu(MenuAddDTO dto, String operUserId, Date operTime) throws Exception {

        return new MenuAddVo("");
    }

    public void mdfMenu(MenuMdfDTO dto, String operUserId, Date operTime) throws Exception {

        //this.domain.mdfMenu(param, operUserId, operTime);
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<MenuVo> getMenuByClient(String clientDictId){
        return null;
    }

    public void delMenu(MenuDelDTO dto, String operUserId, Date operTime) throws Exception {
        if(dto == null){
            return;
        }
        //this.domain.delMenu(dto.getId(), operUserId, operTime);
    }
}
