package org.anson.miniProject.service.sys.permission;

import org.anson.miniProject.core.biz.sys.permission.ResourceBiz;
import org.anson.miniProject.core.model.bo.sys.permission.resource.AddResourceBO;
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
    private ResourceBiz biz;

    @ServiceLog(description = "新增资源", valKey = "name", pkCalssFrom = PkClassFrom.RETURN, mainTableName = Resource.__TABLENAME)
    public AddResourceVO add(AddResourceDTO dto, CommonParam cmParam) throws Exception{
        AddResourceBO bo = AddResourceDTO.toAddResourceBO(dto);
        String id = this.biz.add(bo, cmParam.getLoginUserId(), cmParam.getOperTime());
        return AddResourceVO.builder().id(id).build();
    }

    @ServiceLog(description = "删除资源", valKey = "id", mainTableName = Resource.__TABLENAME)
    public void del(DelResourceDTO dto, CommonParam cmParam) throws Exception{
        this.biz.del(dto.getId(), cmParam.getLoginUserId(), cmParam.getOperTime());
    }
}