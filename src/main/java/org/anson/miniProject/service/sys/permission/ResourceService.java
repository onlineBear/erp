package org.anson.miniProject.service.sys.permission;

import org.anson.miniProject.core.domain.sys.permission.IResourceDomain;
import org.anson.miniProject.core.model.dmo.sys.permission.resource.AddResourceParam;
import org.anson.miniProject.core.model.dto.service.sys.permission.resource.AddResourceDTO;
import org.anson.miniProject.core.model.dto.service.sys.permission.resource.DelResourceDTO;
import org.anson.miniProject.core.model.po.sys.permission.Resource;
import org.anson.miniProject.core.model.vo.sys.permission.resource.AddResourceVO;
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
    private IResourceDomain domain;

    @ServiceLog(description = "新增资源", valKey = "name", pkCalssFrom = PkClassFrom.RETURN, mainTableName = Resource.__TABLENAME)
    public AddResourceVO add(AddResourceDTO dto, CommonParam cmParam) throws Exception{
        AddResourceParam param = AddResourceDTO.toParam(dto);
        String id = this.domain.add(param, cmParam.getLoginUserId(), cmParam.getOperTime());
        return AddResourceVO.builder().id(id).build();
    }

    @ServiceLog(description = "删除资源", valKey = "id", mainTableName = Resource.__TABLENAME)
    public void del(DelResourceDTO dto, CommonParam cmParam) throws Exception{
        this.domain.del(dto.getId(), cmParam.getLoginUserId(), cmParam.getOperTime());
    }
}
