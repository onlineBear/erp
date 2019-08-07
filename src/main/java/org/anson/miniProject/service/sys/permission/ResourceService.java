package org.anson.miniProject.service.sys.permission;

import org.anson.miniProject.core.model.dto.service.sys.permission.resource.AddResourceDTO;
import org.anson.miniProject.core.model.dto.service.sys.permission.resource.DelResourceDTO;
import org.anson.miniProject.core.model.vo.sys.permission.resource.AddResourceVO;
import org.anson.miniProject.domain.sys.permission.resource.IResourceDMService;
import org.anson.miniProject.framework.pojo.CommonParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ResourceService {
    @Autowired
    private IResourceDMService resourceDMService;

    private static final String MAINTABLENAME = "1";

    public AddResourceVO add(AddResourceDTO dto, CommonParam cmParam) throws Exception{
        return AddResourceVO.builder().id("").build();
    }

    public void del(DelResourceDTO dto, CommonParam cmParam) throws Exception{
        //this.domain.del(dto.getId(), cmParam.getLoginUserId(), cmParam.getOperTime());
    }
}
