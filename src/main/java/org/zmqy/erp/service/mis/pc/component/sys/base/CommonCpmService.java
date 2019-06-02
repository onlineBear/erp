package org.zmqy.erp.service.mis.pc.component.sys.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zmqy.erp.mapper.mis.biz.sys.base.CommonMapper;
import org.zmqy.erp.mapper.mis.pc.component.sys.base.CommonCpmMapper;
import org.zmqy.erp.mapper.mis.pc.component.sys.base.MenuCpmMapper;

import java.util.List;
import java.util.Map;

@Service
public class CommonCpmService {
    @Autowired
    private CommonCpmMapper mapper;

    public List<Map> getCommon(Map<String, Object> paramsMap) {
        return this.mapper.getCommon(paramsMap);
    }
}
