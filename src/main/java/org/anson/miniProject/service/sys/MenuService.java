package org.anson.miniProject.service.sys;

import lombok.extern.slf4j.Slf4j;
import org.anson.miniProject.domain.sys.IMenuDomain;
import org.anson.miniProject.mapper.views.sys.MenuViewMapper;
import org.anson.miniProject.model.bo.sys.menu.MenuBo;
import org.anson.miniProject.model.service.menu.*;
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
    private IMenuDomain domain;

    @Autowired
    private MenuViewMapper viewMapper;

    public MenuAddVo addMenu(MenuAddDTO dto, String operUserId, Date operTime){
        MenuBo bo = MenuAddDTO.dto2bo(dto);
        return new MenuAddVo(this.domain.addMenu(bo, operUserId, operTime));
    }

    public void mdfMenu(MenuMdfDTO dto, String operUserId, Date operTime){
        MenuBo bo = MenuMdfDTO.dto2bo(dto);
        this.domain.mdfMenu(bo, operUserId, operTime);
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<MenuVo> getMenuByClient(String clientDictId){
        return this.viewMapper.selMenuByClient(clientDictId);
    }

    public void delMenu(MenuDelDTO dto){
        if(dto == null){
            return;
        }
        this.domain.delMenu(dto.getId());
    }
}
