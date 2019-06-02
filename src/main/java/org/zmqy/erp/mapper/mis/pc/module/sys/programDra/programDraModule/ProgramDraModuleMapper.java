package org.zmqy.erp.mapper.mis.pc.module.sys.programDra.programDraModule;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ProgramDraModuleMapper {
    List<Map> getProgramDraList(@Param("langId")String langId);
    List<Map> getProgramDraDtlList(@Param("programDraId")String programDraId, @Param("langId")String langId);
    List<Map> getNode(@Param("programDraId")String programDraId, @Param("langId")String langId);
    List<Map> getLink(@Param("programDraId")String programDraId, @Param("langId")String langId);

}
