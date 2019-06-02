package org.zmqy.erp.mapper.mis.pc.module.sys.menu;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface MenuModuleMapper {

    List<Map> getMenuList(@Param("langId")String langId);

    List<Map> getResourceByMenu(@Param("langId")String langId, @Param("menuId")String menuId);
}
