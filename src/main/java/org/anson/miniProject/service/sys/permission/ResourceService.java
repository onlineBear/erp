package org.anson.miniProject.service.sys.permission;

import org.anson.miniProject.core.model.dto.service.sys.permission.resource.AddResourceDTO;
import org.anson.miniProject.core.model.dto.service.sys.permission.resource.DelResourceDTO;
import org.anson.miniProject.core.model.param.sys.permission.resource.AddResourceParam;
import org.anson.miniProject.core.model.po.sys.permission.Resource;
import org.anson.miniProject.core.model.vo.sys.permission.resource.AddResourceVO;
import org.anson.miniProject.domain.sys.permission.resource.IResourceDMService;
import org.anson.miniProject.framework.log.service.PkClassFrom;
import org.anson.miniProject.framework.log.service.ServiceLog;
import org.anson.miniProject.framework.pojo.CommonParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ResourceService {
    @Autowired
    private IResourceDMService resourceDMService;

    private static final String MAINTABLENAME = Resource.__TABLENAME;

    @ServiceLog(description = "新增资源", valKey = "name",
            pkCalssFrom = PkClassFrom.RETURN, pkKey = "id",
            mainTableName = MAINTABLENAME)
    public AddResourceVO add(AddResourceDTO dto, CommonParam cmParam) throws Exception{
        AddResourceParam param = AddResourceDTO.toParam(dto);
        //String id = this.domain.add(param, cmParam.getLoginUserId(), cmParam.getOperTime());
        return AddResourceVO.builder().id("").build();
    }

    public void del(DelResourceDTO dto, CommonParam cmParam) throws Exception{
        //this.domain.del(dto.getId(), cmParam.getLoginUserId(), cmParam.getOperTime());
    }
}
