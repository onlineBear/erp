package org.zmqy.erp.mapper.mis.pc.module.sys.role;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface RoleModuleMapper {

    List<Map> getRoleList(@Param("langId")String langId);

    List<Map> getUserByRole(@Param("roleId")String roleId);
    List<Map> getResByRole(@Param("langId")String langId, @Param("roleId")String roleId);
    List<Map> getProgramDraByRole(@Param("langId")String langId, @Param("roleId")String roleId);
}
