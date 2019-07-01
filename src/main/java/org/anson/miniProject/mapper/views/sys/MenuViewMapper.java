package org.anson.miniProject.mapper.views.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.anson.miniProject.model.entity.sys.Menu;
import org.anson.miniProject.model.service.menu.MenuVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuViewMapper extends BaseMapper<Menu> {
    List<MenuVo> selMenuByClient(@Param("clientDictId") String clientDictId);
}
