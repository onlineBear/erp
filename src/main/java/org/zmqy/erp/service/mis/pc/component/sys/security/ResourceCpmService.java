package org.zmqy.erp.service.mis.pc.component.sys.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zmqy.erp.mapper.mis.pc.component.sys.base.ResourceCpmMapper;
import org.zmqy.erp.tool.util.common.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ResourceCpmService {
    @Autowired
    private ResourceCpmMapper mapper;

    public Map getMenuResource(String userId, String langId, String menuNo) {
        List<Map> list = this.mapper.getMenuResource(userId, langId, menuNo);

        Map rsMap = new HashMap();

        if (list != null && list.size() > 0) {
            for (Map oneMap : list) {
                String buttonId = (String) oneMap.get("buttonId");

                if (StringUtil.isEmpty(buttonId)) {
                    continue;
                }

                if (buttonId.equals("resOper-sel")) {
                    rsMap.put("sel", oneMap);
                } else if (buttonId.equals("resOper-add")) {
                    rsMap.put("add", oneMap);
                } else if (buttonId.equals("resOper-mdf")) {
                    rsMap.put("mdf", oneMap);
                } else if (buttonId.equals("resOper-del")) {
                    rsMap.put("del", oneMap);
                } else if (buttonId.equals("resOper-import")) {
                    rsMap.put("import", oneMap);
                } else if (buttonId.equals("resOper-export")) {
                    rsMap.put("export", oneMap);
                } else if (buttonId.equals("resOper-print")) {
                    rsMap.put("print", oneMap);
                } else if (buttonId.startsWith("resOper-ck")) {
                    List<Map> ckList = (List) rsMap.get("ck");
                    if (ckList == null || ckList.size() <= 0) {
                        List<Map> ckMap = new ArrayList<>();
                        ckMap.add(oneMap);
                        rsMap.put("ck", ckMap);
                    } else {
                        ckList.add(oneMap);
                    }
                } else if (buttonId.startsWith("resOper-bhCk")) {
                    List<Map> ckList = (List) rsMap.get("bhCk");
                    if (ckList == null || ckList.size() <= 0) {
                        List<Map> ckMap = new ArrayList<>();
                        ckMap.add(oneMap);
                        rsMap.put("bhCk", ckMap);
                    } else {
                        ckList.add(oneMap);
                    }
                } else if (buttonId.startsWith("resOper-unCk")) {
                    List<Map> ckList = (List) rsMap.get("unCk");
                    if (ckList == null || ckList.size() <= 0) {
                        List<Map> ckMap = new ArrayList<>();
                        ckMap.add(oneMap);
                        rsMap.put("unCk", ckMap);
                    } else {
                        ckList.add(oneMap);
                    }
                } else if (buttonId.startsWith("resOper-bhDel")) {
                    List<Map> ckList = (List) rsMap.get("bhDel");
                    if (ckList == null || ckList.size() <= 0) {
                        List<Map> ckMap = new ArrayList<>();
                        ckMap.add(oneMap);
                        rsMap.put("bhDel", ckMap);
                    } else {
                        ckList.add(oneMap);
                    }
                }

            }
        }

        return rsMap;
    }
}
