package org.anson.miniProject.core.mapper.views.sys;

import org.anson.miniProject.core.model.service.menu.MenuVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuViewMapper {
    List<MenuVo> selMenuByClient(@Param("clientKey") String clientDictId);
}
