package org.zmqy.erp.mapper.mis.pc.component.sys.base;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CommonCpmMapper {
    public List<Map> getCommon(Map<String, Object> paramsMap);
}
