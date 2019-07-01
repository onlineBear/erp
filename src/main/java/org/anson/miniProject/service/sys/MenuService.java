package org.anson.miniProject.service.sys;

import lombok.extern.slf4j.Slf4j;
import org.anson.miniProject.domain.sys.IMenuDomain;
import org.anson.miniProject.model.bo.sys.menu.MenuAddBo;
import org.anson.miniProject.model.bo.sys.menu.MenuMdfBo;
import org.anson.miniProject.model.dto.sys.menu.MenuAddDTO;
import org.anson.miniProject.model.dto.sys.menu.MenuMdfDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class MenuService {
    @Autowired
    private IMenuDomain domain;

    public String addMenu(MenuAddDTO dto, String operUserId, Date operTime){
        MenuAddBo bo = MenuAddDTO.dto2bo(dto);
        return this.domain.addMenu(bo, operUserId, operTime);
    }

    public void mdfMenu(MenuMdfDTO dto, String operUserId, Date operTime){
        MenuMdfBo bo = MenuMdfDTO.dto2bo(dto);
        this.domain.mdfMenu(bo, operUserId, operTime);
    }
}
