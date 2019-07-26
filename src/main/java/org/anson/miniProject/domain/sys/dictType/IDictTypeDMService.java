package org.anson.miniProject.domain.sys.dictType;

import org.anson.miniProject.domain.sys.dictType.cmd.AddDictTypeCMD;
import org.anson.miniProject.domain.sys.dictType.cmd.UpdDictTypeCMD;

public interface IDictTypeDMService {
    String addDictType(AddDictTypeCMD cmd) throws Exception;
    void updateDictType(UpdDictTypeCMD cmd) throws Exception;
    void delDictType(String id) throws Exception;
}
