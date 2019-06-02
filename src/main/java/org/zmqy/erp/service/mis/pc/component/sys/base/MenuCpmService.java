package org.zmqy.erp.service.mis.pc.component.sys.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zmqy.erp.mapper.mis.pc.component.sys.base.MenuCpmMapper;

import java.util.List;
import java.util.Map;

@Service
public class MenuCpmService {
    @Autowired
    private MenuCpmMapper mapper;

    public List<Map> getUserMenu(String userId, String langId, String storeId) {
        return this.mapper.getUserMenu(userId, langId, storeId);
    }

    public List<Map> getMenu(Map<String, Object> paramsMap) {
        String userId = (String) paramsMap.get("loginUserId");
        String langId = (String) paramsMap.get("loginLangId");
        String storeId = (String) paramsMap.get("loginStoreId");
        String menuNoOrName = paramsMap.get("menuNoOrName") + "";
        paramsMap.put("menuNoOrName", "%" + menuNoOrName + "%");
        return mapper.getMenu(paramsMap);
    }
}
