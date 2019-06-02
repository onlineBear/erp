package org.zmqy.erp.service.mis.pc.component.sys.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zmqy.erp.mapper.mis.pc.component.sys.base.RegionCpmMapper;

import java.util.List;
import java.util.Map;

@Service
public class RegionCpmService {
    @Autowired
    private RegionCpmMapper mapper;

        public List<Map<String, String>> getList(String langId){
        return this.mapper.getList(langId);
    }
}
