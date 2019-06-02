package org.zmqy.erp.domain.mis.programDra;

import org.zmqy.erp.model.mis.bo.sys.flow.FlowBo;
import org.zmqy.erp.model.mis.bo.sys.programDra.ProgramDraBo;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IProgramDraDoMain {
    String add(ProgramDraBo bo, String operUserId, Date operTime)throws Exception;
    void mdfProgramDra(ProgramDraBo bo, String operUserId, Date operTime)throws Exception;
    void delProgramDra(String programDraId, String langId)throws Exception;
    void mdfSeq(ProgramDraBo programDraBo,String operUserId, Date operTime)throws Exception;
}
