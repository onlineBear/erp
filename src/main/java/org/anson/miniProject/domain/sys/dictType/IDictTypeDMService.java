package org.anson.miniProject.domain.sys.dictType;

import org.anson.miniProject.domain.sys.dictType.cmd.AddDictTypeCMD;
import org.anson.miniProject.domain.sys.dictType.cmd.UpdateDictTypeCMD;

public interface IDictTypeDMService {
    String addDictType(AddDictTypeCMD cmd) throws Exception;
    void updateDictType(UpdateDictTypeCMD cmd) throws Exception;
}
